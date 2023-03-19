package com.charter.commons.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@Aspect
@Component
public class LoggingAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  /** loggingPointcut. */
  @Pointcut(
      """
              within(@org.springframework.stereotype.Repository *)\
              || within(@org.springframework.stereotype.Service *)\
              || within(@org.springframework.web.bind.annotation.RestController *)""")
  public void loggingPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  /**
   * loggingAround.
   *
   * @param proceedingJoinPoint a {@link org.aspectj.lang.ProceedingJoinPoint} object
   * @return a {@link java.lang.Object} object
   * @throws java.lang.Throwable if any.
   */
  @Around("loggingPointcut()")
  public Object loggingAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    var args = proceedingJoinPoint.getArgs();

    var methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    var methodSignatureDeclaringType = methodSignature.getDeclaringType().getName();
    var methodSignatureName = methodSignature.getName();
    var methodSignatureParameterNames = methodSignature.getParameterNames();

    var methodParametersWithValue =
        getMethodParametersWithValue(methodSignatureParameterNames, args);

    LOGGER.info(
        "Executing: {}.{} - {}",
        methodSignatureDeclaringType,
        methodSignatureName,
        methodParametersWithValue);

    var start = System.currentTimeMillis();

    var proceed = proceedingJoinPoint.proceed();

    var executionTime = System.currentTimeMillis() - start;

    var executionTimeDuration = Duration.ofMillis(executionTime);

    var formattedExecutionTimeDuration =
        String.format(
            "%d:%02d:%02d:%03d",
            executionTimeDuration.toHours(),
            executionTimeDuration.toMinutesPart(),
            executionTimeDuration.toSecondsPart(),
            executionTimeDuration.toMillisPart());

    LOGGER.info(
        "Executed: {}.{} - {} - time: {} ms - {}",
        methodSignatureDeclaringType,
        methodSignatureName,
        methodParametersWithValue,
        executionTime,
        formattedExecutionTimeDuration);

    return proceed;
  }

  /**
   * loggingAfterThrowing.
   *
   * @param joinPoint a {@link org.aspectj.lang.JoinPoint} object
   * @param throwable a {@link java.lang.Throwable} object
   */
  @AfterThrowing(pointcut = "loggingPointcut()", throwing = "throwable")
  public void loggingAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
    var args = joinPoint.getArgs();

    var methodSignature = (MethodSignature) joinPoint.getSignature();

    var methodSignatureDeclaringType = methodSignature.getDeclaringType().getName();
    var methodSignatureName = methodSignature.getName();
    var methodSignatureParameterNames = methodSignature.getParameterNames();

    var methodParametersWithValue =
        getMethodParametersWithValue(methodSignatureParameterNames, args);

    var mappedThrowable = mapThrowable(throwable);

    LOGGER.error(
        "Executed with exception: {}.{} - {} - {}",
        methodSignatureDeclaringType,
        methodSignatureName,
        methodParametersWithValue,
        mappedThrowable);
  }

  private static String getMethodParametersWithValue(String[] parameterNames, Object[] args) {
    var stringJoiner = new StringJoiner(", ", "Parameters: [", "]");

    IntStream.range(0, parameterNames.length)
        .forEach(
            i -> {
              var parameterName = parameterNames[i];
              var arg = String.format("%s", args[i]);

              stringJoiner.add(String.format("%s = '%s'", parameterName, arg));
            });

    return stringJoiner.toString();
  }

  private static String mapThrowable(Throwable throwable) {
    var stringJoiner = new StringJoiner("\n", "Exception: \n", "\n");

    var stringWriter = new StringWriter();

    var printWriter = new PrintWriter(stringWriter);

    throwable.printStackTrace(printWriter);

    stringJoiner.add(stringWriter.toString());

    return stringJoiner.toString();
  }
}
