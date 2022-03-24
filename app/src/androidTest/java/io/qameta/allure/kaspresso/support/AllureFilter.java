package io.qameta.allure.kaspresso.support;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import java.util.Objects;
import java.util.Optional;

import io.qameta.allure.kotlin.AllureId;
import io.qameta.allure.testfilter.TestPlan;
import io.qameta.allure.testfilter.TestPlanUnknown;
import io.qameta.allure.testfilter.TestPlanV1_0;

public class AllureFilter extends Filter {

    private final TestPlan testPlan;

    public AllureFilter() {
        this.testPlan = new AndroidTestPlanSupplier().supply().orElse(new TestPlanUnknown());
    }

    @Override
    public String describe() {
        if (testPlan instanceof TestPlanV1_0) {
            final Object[] ids = ((TestPlanV1_0) testPlan).getTests().stream()
                    .map(TestPlanV1_0.TestCase::getId).toArray();
            return String.format("include ids: %s", ids);
        }
        return "allure filter skipped";
    }

    @Override
    public boolean shouldRun(final Description description) {
        if (Objects.isNull(description.getMethodName())) {
            return true;
        }
        if (testPlan instanceof TestPlanV1_0) {
            return shouldRun(description, (TestPlanV1_0) testPlan);
        }
        return true;
    }

    private boolean shouldRun(final Description description, final TestPlanV1_0 testPlanWithTests) {
        final Optional<String> allureId = Optional
                .ofNullable(description.getAnnotation(AllureId.class))
                .map(AllureId::value);
        final String selector = getSelector(description.getClassName(), description.getMethodName());
        if (allureId.isPresent()) {
            final String id = allureId.get();
            return testPlanWithTests.getTests().stream()
                    .map(TestPlanV1_0.TestCase::getId)
                    .anyMatch(id::equals);
        } else {
            return testPlanWithTests.getTests().stream()
                    .map(TestPlanV1_0.TestCase::getSelector)
                    .anyMatch(selector::equals);
        }
    }

    private String getSelector(final String className, final String methodName) {
        return String.format("%s.%s",
                className,
                methodName);
    }

}
