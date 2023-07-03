import com.github.ajalt.clikt.core.CliktCommand
import kotlin.random.Random
import kotlin.system.measureTimeMillis

const val DEBUFF_FAR = "\uD83E\uDDCD||\uD83E\uDDCD"
const val DEBUFF_MIDDLE = "|\uD83E\uDDCD\uD83E\uDDCD|"

@Suppress("ClassName")
class Phase2_PartySynergy : CliktCommand(help = "Phase 2 - Party Synergy", name = "p2") {
    override fun run() {
        echo("Phase 2 - Party Synergy")
        echo("공략방식 - 09STOP")

        echo(" H1(1) T1(2) T2(3) D1(4) D2(5) D3(6) D4(7) H2(8)")

        echo("본인의 포지션을 숫자(1~8)로 입력하세요: ", false)

        val position = readln().toIntOrNull() ?: run {
            echo("유효하지 않은 입력입니다.")
            return
        }

        while (true) {
            val isRight = printQuestion(position)
            echo("정답? $isRight")
        }
    }

    private fun printQuestion(position: Int): Boolean {
        val markerList = listOf("") + listOf("▢", "◯", "▽", "⨯", "◯", "⨯", "▢", "▽").shuffled()
        val isMiddle = Random.nextBoolean()

        echo("        ${getMiddle(isMiddle)} ")
        echo(" ${markerList[1]}  ${markerList[2]}  ${markerList[3]}  ${markerList[4]}  ${markerList[5]}  ${markerList[6]}  ${markerList[7]}  ${markerList[8]}")
        echo("H1 T1 T2 D1 D2 D3 D4 H2")

        val ansPositionArray = IntArray(9) { -1 }
        val positionOrder = (1..8)

        positionOrder.forEachIndexed { idx, userPosition ->
            var answerPosition = markerList[userPosition].toPosition()

            if (ansPositionArray[answerPosition] == -1) {
                ansPositionArray[answerPosition] = userPosition
            } else {
                ansPositionArray[answerPosition + 4] = userPosition
            }
//            echo("[$idx]User: $userPosition / ${ansPositionArray.toList()}")
        }

        if (!isMiddle) {
            ansPositionArray.swap(5, 8)
            ansPositionArray.swap(6, 7)
        }
//        echo("${ansPositionArray.toList()}")

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

    private fun String.toPosition(): Int = when (this) {
        "◯" -> 1
        "▽" -> 2
        "▢" -> 3
        "⨯" -> 4
        else -> -1
    }

    private fun getMiddle(isMiddle: Boolean) =
        if (isMiddle) DEBUFF_MIDDLE else DEBUFF_FAR
}