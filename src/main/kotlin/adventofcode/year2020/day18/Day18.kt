package adventofcode.year2020.day18

import java.lang.IllegalArgumentException

typealias Expression = List<String>

object Task1 {
    fun sumOfExpressions(input: String): Long {
        return sumOfExpressions(input, ::evaluateExpressionWithSamePrecedence)
    }
}

object Task2 {
    fun sumOfExpressions(input: String): Long {
        return sumOfExpressions(input, ::evaluateExpressionWithPlusHigherPrecedence)
    }
}

private fun sumOfExpressions(input: String, evaluationFunc: (Expression) -> (Long)): Long {
    return input
        .lines()
        .asSequence()
        .map { it.replace("(", "( ").replace(")", " )") }
        .map { it.split(" ") }
        .map { evaluateExpressionWithParenthesis(it, evaluationFunc) }
        .flatten()
        .map { it.toLong() }
        .sum()
}

private fun evaluateExpressionWithParenthesis(expression: Expression, evaluationFunc: (Expression) -> (Long)): Expression {
    val firstStartParenthesisIndex = expression.indexOf("(")

    return if (firstStartParenthesisIndex != -1) {
        val matchingEndParenthesisIndex = findMatchingEndParenthesisIndex(expression, firstStartParenthesisIndex)

        val expressionBefore = expression.subList(0, firstStartParenthesisIndex)
        val parenthesisExpression = evaluateExpressionWithParenthesis(expression.subList(firstStartParenthesisIndex + 1, matchingEndParenthesisIndex), evaluationFunc)
        val expressionAfter = expression.subList(matchingEndParenthesisIndex + 1, expression.size)

        evaluateExpressionWithParenthesis(expressionBefore + parenthesisExpression + expressionAfter, evaluationFunc)
    } else {
        listOf(evaluationFunc(expression).toString())
    }
}

private fun evaluateExpressionWithSamePrecedence(expression: Expression): Long {
    // The list will always start with a digit and can be split into even chunks of 2 after the first digit
    return expression.drop(1).chunked(2).fold(expression[0].toLong()) { value, list ->
        when (list[0]) {
            "+" -> value + list[1].toLong()
            "*" -> value * list[1].toLong()
            else -> throw IllegalArgumentException("Could not evaluate expression: $expression")
        }
    }
}

private fun evaluateExpressionWithPlusHigherPrecedence(expression: Expression): Long {
    var evaluated = expression

    // Calculate all the additions first
    while (evaluated.contains("+")) {
        val index = evaluated.indexOf("+")
        val sum = evaluated[index - 1].toLong() + evaluated[index + 1].toLong()

        val beforeList = if (index - 2 >= 0) evaluated.subList(0, index - 1) else listOf()
        val afterList = if (index + 2 <= evaluated.size - 1) evaluated.subList(index + 2, evaluated.size) else listOf()

        evaluated = beforeList + sum.toString() + afterList
    }

    // Now we only have the multiplications left
    return evaluated.filterNot { it == "*" }.map { it.toLong() }.fold(1L) { product, n -> product * n }
}

private fun findMatchingEndParenthesisIndex(expression: Expression, start: Int): Int {
    val str = expression.joinToString("")
    val indexOfFirstParenthesis = str.indexOf('(', start)

    var count = 0

    for (i in indexOfFirstParenthesis until str.length) {
        when (str[i]) {
            '(' -> count++
            ')' -> count--
        }

        if (count == 0) return i - (indexOfFirstParenthesis - start)
    }

    throw IllegalArgumentException("Could not find matching end parenthesis for expression: $expression")
}