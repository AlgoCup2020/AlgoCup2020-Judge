package org.algohub.engine

import org.algohub.engine.judge.Function

internal object JavaCodeGenerator {

    private val CUSTOM_IMPORT = """import java.util.*;
import org.algohub.engine.type.*;

"""

    /**
     * Generate an empty function with comments.

     * @param function Function prototype
     * *
     * @return source code of a empty function
     */
    @Throws(ClassNotFoundException::class)
    fun generateEmptyFunction(function: Function)=  "${CUSTOM_IMPORT}public class Solution {\n${generateFunction(function)}}\n"

    /**
     * Generate a type declaration.
     * The parent type should be ARRAY by default
     *
     *
     *
     * post order, recursive.

     * @param type the type
     * *
     * @return type declaration
     */
    private fun generateJavaTypeDeclaration(type: String)=  if ("void" == type) type else classOrPrimitiveName(type)

    private fun classOrPrimitiveName(type: String): String {
        val typeName = Class.forName(type).simpleName

        return when (typeName) {
            "Boolean" -> Boolean::class.javaPrimitiveType!!.simpleName
            "Long" -> Long::class.javaPrimitiveType!!.simpleName
            "Integer" -> Int::class.javaPrimitiveType!!.simpleName
            "Short" -> Short::class.javaPrimitiveType!!.simpleName
            "Double" -> Double::class.javaPrimitiveType!!.simpleName
            "Float" -> Float::class.javaPrimitiveType!!.simpleName
            else -> typeName
        }
    }

    private fun generateParameterDeclaration(type: String, parameterName: String) =
            String.format("%s %s", generateJavaTypeDeclaration(type), parameterName)

    private fun generateFunction(function: Function): String {
        val result = StringBuilder()

        functionComment(function, result)
        functionBody(function, result)
        deleteUnnecessaryLastComma(result)

        return result.toString()
    }

    private fun deleteUnnecessaryLastComma(result: StringBuilder) {
        result.delete(result.length - 2, result.length)
        result.append(") {\n")
        appendIndentation(result, "    // Write your code here\n")
        appendIndentation(result, "}\n")
    }

    private fun functionBody(function: Function, result: StringBuilder) {
        appendIndentation(result, "public ")
        result.append(generateJavaTypeDeclaration(function.returnStatement.type))
        result.append(" ").append(function.name).append("(")

        for (p in function.parameters) {
            result.append(generateParameterDeclaration(p.type, p.name))
                    .append(", ")
        }
    }

    private fun functionComment(function: Function, result: StringBuilder) {
        appendIndentation(result, "/**\n")

        for (p in function.parameters) {
            appendIndentation(result, " * @param ${p.name} ${p.comment}\n")
        }

        appendIndentation(result, " * @return ${function.returnStatement.comment}\n")
        appendIndentation(result, " */\n")
    }

    private fun appendIndentation(sourceCode: StringBuilder, sourceCodeLine: String) {
        sourceCode.append("    ").append(sourceCodeLine)
    }
}
