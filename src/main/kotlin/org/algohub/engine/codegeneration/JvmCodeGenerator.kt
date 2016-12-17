package org.algohub.engine.codegeneration

import org.algohub.engine.judge.Function

interface JvmCodeGenerator {
    fun functionComment(function: Function): String {
        val result = StringBuilder()
        result.append("/**\n")

        for (parameter in function.parameters) {
            appendIndentation(result, " * @param ${parameter.name} ${parameter.comment}\n")
        }

        appendIndentation(result, " * @return ${function.returnStatement.comment}\n")
        appendIndentation(result, " */")

        return result.toString()
    }

    private fun appendIndentation(sourceCode: StringBuilder, sourceCodeLine: String) {
        sourceCode.append("    ").append(sourceCodeLine)
    }

    fun deleteLastUnnecessaryComma(result: StringBuilder) {
        result.delete(result.length - 2, result.length)
    }
}