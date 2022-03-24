package io.qameta.allure.kaspresso.support;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import io.qameta.allure.testfilter.TestPlan;
import io.qameta.allure.testfilter.TestPlanSupplier;

public class AndroidTestPlanSupplier implements TestPlanSupplier {

    private final static String TEST_PLAN_FILE_PATH = "/data/data/testplan.json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Override
    public Optional<TestPlan> supply() {
        return tryGetPath()
                .filter(Files::exists)
                .filter(Files::isRegularFile)
                .flatMap(this::readTestPlan);
    }

    private Optional<TestPlan> readTestPlan(final Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            return Optional.of(OBJECT_MAPPER.readValue(stream, TestPlan.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<Path> tryGetPath() {
        try {
            return Optional.of(Paths.get(TEST_PLAN_FILE_PATH));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
