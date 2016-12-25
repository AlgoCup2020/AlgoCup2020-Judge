package com.jalgoarena.judge

import com.jalgoarena.utils.BetterObjects
import com.jalgoarena.utils.ThrowableEnhancements
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.concurrent.Callable

internal class JudgeTask(private val clazz: Any, private val method: Method, private val testCases: Array<InternalTestCase>)
    : Callable<List<Boolean>>, ThrowableEnhancements {

    private val LOG = LoggerFactory.getLogger(this.javaClass)

    override fun call(): List<Boolean> = testCases.map { judge(clazz, method, it) }

    private fun judge(clazz: Any, method: Method, testCase: InternalTestCase): Boolean {

        val output: Any?
        val input: Array<Any?>

        try {
            input = testCase.input
            output = method.invoke(clazz, *input)
        } catch (e: IllegalAccessException) {
            val cause = getCause(e)
            LOG.error("Error during processing of solution", cause)
            throw InterruptedException(cause.javaClass.name + ": " + cause.message)
        } catch (e: InvocationTargetException) {
            val cause = getCause(e)

            if (cause is NullPointerException) {
                return false
            }

            LOG.error("Error during processing of solution", cause)
            throw InterruptedException(cause.javaClass.name + ": " + cause.message)
        } catch (e: IllegalArgumentException) {
            val cause = getCause(e)
            LOG.error("Error during processing of solution", cause)
            throw InterruptedException(cause.javaClass.name + ": " + cause.message)
        }

        return BetterObjects.equalForObjectsOrArrays(
                testCase.output,
                if (testCase.returnsVoid) input[0] else output
        )
    }
}
