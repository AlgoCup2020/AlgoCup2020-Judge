package com.jalgoarena.codegeneration

import com.jalgoarena.ApplicationConfiguration
import com.jalgoarena.domain.Function
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.inject.Inject

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(ApplicationConfiguration::class))
class KotlinCodeGeneratorTest {

    @Inject
    lateinit var kotlinCodeGenerator: KotlinCodeGenerator

    @Test
    fun generates_skeleton_function_for_TWO_SUM() {
        val twoSumGenerated = kotlinCodeGenerator.generateEmptyFunction(TWO_SUM)

        val twoSumExpected = """import java.util.*
import com.jalgoarena.type.*

class Solution {
    /**
     * @param numbers An array of Integers
     * @param target target = numbers[index1] + numbers[index2]
     * @return [index1 + 1, index2 + 1] (index1 < index2)
     */
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        // Write your code here
    }
}
"""
        assertThat(twoSumGenerated).isEqualTo(twoSumExpected)
    }

    @Test
    fun generates_skeleton_function_for_WORD_LADDER() {
        val wordLadderGenerated = kotlinCodeGenerator.generateEmptyFunction(WORD_LADDER)

        val wordLadderExpected = """import java.util.*
import com.jalgoarena.type.*

class Solution {
    /**
     * @param begin_word the begin word
     * @param end_word the end word
     * @param dict the dictionary
     * @return The shortest length
     */
    fun ladderLength(begin_word: String, end_word: String, dict: HashSet): Int {
        // Write your code here
    }
}
"""
        assertThat(wordLadderGenerated).isEqualTo(wordLadderExpected)
    }

    @Test
    fun generates_skeleton_function_for_VERTICAL_FLIP() {
        val verticalFlipGenerated = kotlinCodeGenerator.generateEmptyFunction(VERTICAL_FLIP)

        val verticalFlipExpected = """import java.util.*
import com.jalgoarena.type.*

class Solution {
    /**
     * @param matrix Image matrix to flip
     * @return Operation in-place
     */
    fun flipVerticalAxis(matrix: Array<IntArray>) {
        // Write your code here
    }
}
"""
        assertThat(verticalFlipGenerated).isEqualTo(verticalFlipExpected)
    }

    companion object {
        private val TWO_SUM = Function("twoSum",
                Function.Return("[I",
                        "[index1 + 1, index2 + 1] (index1 < index2)"),
                arrayOf(Function.Parameter("numbers", "[I", "An array of Integers"),
                        Function.Parameter("target", "java.lang.Integer",
                                "target = numbers[index1] + numbers[index2]")
                )
        )

        private val WORD_LADDER = Function("ladderLength",
                Function.Return("java.lang.Integer", "The shortest length"),
                arrayOf(Function.Parameter("begin_word", "java.lang.String", "the begin word"),
                        Function.Parameter("end_word", "java.lang.String", "the end word"),
                        Function.Parameter("dict", "java.util.HashSet", "the dictionary")
                )
        )

        private val VERTICAL_FLIP = Function("flipVerticalAxis",
                Function.Return("void", "Operation in-place"),
                arrayOf(Function.Parameter("matrix", "[[I", "Image matrix to flip")))
    }
}
