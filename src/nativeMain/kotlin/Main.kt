import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class FF14TOPSimulator : CliktCommand() {
    override fun run() = Unit
}

const val DEBUFF_FAR = "\uD83E\uDDCD||\uD83E\uDDCD"
const val DEBUFF_MIDDLE = "|\uD83E\uDDCD\uD83E\uDDCD|"

class Phase2PartySynergy : CliktCommand(help = "Phase 2 - Party Synergy", name = "p2") {
    override fun run() {
        echo("Phase 2 - Party Synergy")
        echo("공략방식 - 누케마루")
        echo("A를 12시로 보시나요?(Y/N) ", false)


        val isAto12 = readln().uppercase() == "Y"

        if (isAto12) {
            echo("         A    ")
            echo(" (1)D4      D3(5)")
            echo("(2)H2        D2(6)")
            echo("(3)H1        D1(7)")
            echo(" (4)ST      MT(8)")
        } else {
            echo("         C    ")
            echo(" (1)MT      ST(5)")
            echo("(2)D1        H1(6)")
            echo("(3)D2        H2(7)")
            echo(" (4)D3      D4(8)")
        }

        echo("본인의 포지션을 숫자(1~8)로 입력하세요: ", false)

        val position = readln().toIntOrNull() ?: run {
            echo("유효하지 않은 입력입니다.")
            return
        }

        while (true) {
            val isRight = printQuestion(isAto12, position)
            echo("정답? $isRight")
        }
    }

    private fun printQuestion(isAto12: Boolean, position: Int): Boolean {
        val markerList = listOf("") + listOf("▢", "◯", "▽", "⨯", "◯", "⨯", "▢", "▽").shuffled()
        val isMiddle = Random.nextBoolean()

        echo("    ${markerList[1]}        ${markerList[5]}")
        echo("   ${markerList[2]}          ${markerList[6]}")
        echo("   ${markerList[3]}  ${getMiddle(isMiddle)}  ${markerList[7]}")
        echo("    ${markerList[4]}        ${markerList[8]}")

        val ansPositionArray = IntArray(9) { -1 }
        val positionOrder = if (isAto12) {
            (8 downTo 1)
        } else {
            (1..8)
        }

        positionOrder.forEachIndexed { idx, userPosition ->
            var answerPosition = markerList[userPosition].toPosition()

            if (idx > 3) answerPosition += 4
            val swapPosition = if (idx > 3) -4 else 4

            if (ansPositionArray[answerPosition] == -1) {
                ansPositionArray[answerPosition] = userPosition
            } else {
                ansPositionArray[answerPosition + swapPosition] = userPosition
            }
//            echo("[$idx]User: $userPosition / ${ansPositionArray.toList()}")
        }

        if (!isMiddle) {
            ansPositionArray.swap(5, 8)
            ansPositionArray.swap(6, 7)
        }
        echo("${ansPositionArray.toList()}")

        echo("가야될 위치는? ", false)

        lateinit var userAns: String

        val elapsedTime = measureTimeMillis {
            userAns = readln()
        }

        var userAnsPos = 0

        if (userAns[0].uppercase() == "R") {
            userAnsPos += 4
        }

        userAnsPos += userAns[1].toString().toInt()

        val isMiddleFarCorrect = if (isMiddle) {
            userAns[2].uppercase() == "M"
        } else {
            userAns[2].uppercase() == "F"
        }

        val trueAnswer = ansPositionArray.indexOf(position).let {
            val answerBuilder = StringBuilder()

            if (it > 4) {
                answerBuilder.append("R")
            } else {
                answerBuilder.append("L")
            }

            answerBuilder.append(if (it > 4) it - 4 else it)

            if (isMiddle) {
                answerBuilder.append("M")
            } else {
                answerBuilder.append("F")
            }

            answerBuilder.toString()
        }

        echo("Answer: $trueAnswer, ${elapsedTime / 1000.0}초 걸렸습니다.")
        return (ansPositionArray[userAnsPos] == position) && isMiddleFarCorrect
    }
}

private fun IntArray.swap(first: Int, second: Int) {
    val temp = get(first)

    this[first] = get(second)
    this[second] = temp
}

private fun String.toPosition(): Int = when (this) {
    "◯" -> 1
    "⨯" -> 2
    "▽" -> 3
    "▢" -> 4
    else -> -1
}

private fun getMiddle(isMiddle: Boolean) =
    if (isMiddle) DEBUFF_MIDDLE else DEBUFF_FAR

private fun <E> ArrayList<E>.randomPop(): E {
    val randomElement = random()
    this.remove(randomElement)

    return randomElement
}

fun main(args: Array<String>) = FF14TOPSimulator()
    .subcommands(Phase2PartySynergy())
    .main(args)