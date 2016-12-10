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
    @JvmStatic
    fun generateEmptyFunction(function: Function): String {
        return "${CUSTOM_IMPORT}public class Solution {\n${generateFunction(function)}}\n"
    }

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
    @Throws(ClassNotFoundException::class)
    private fun generateJavaTypeDeclaration(type: String): String {
        return if ("void" == type) type else classOrPrimitiveName(type)
    }

    @Throws(ClassNotFoundException::class)
    private fun classOrPrimitiveName(type: String): String {
        val typeName = Class.forName(type).simpleName

        when (typeName) {
            "Boolean" -> return Boolean::class.javaPrimitiveType!!.getSimpleName()
            "Long" -> return Long::class.javaPrimitiveType!!.getSimpleName()
            "Integer" -> return Int::class.javaPrimitiveType!!.getSimpleName()
            "Short" -> return Short::class.javaPrimitiveType!!.getSimpleName()
            "Double" -> return Double::class.javaPrimitiveType!!.getSimpleName()
            "Float" -> return Float::class.javaPrimitiveType!!.getSimpleName()
            else -> return typeName
        }
    }

    @Throws(ClassNotFoundException::class)
    private fun generateParameterDeclaration(type: String, parameterName: String): String {
        val typeDeclaration = generateJavaTypeDeclaration(type)
        return String.format("%s %s", typeDeclaration, parameterName)
    }

    @Throws(ClassNotFoundException::class)
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

    @Throws(ClassNotFoundException::class)
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
        sourceCode.append("    ")
        sourceCode.append(sourceCodeLine)
    }
}
