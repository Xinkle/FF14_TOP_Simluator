import com.github.ajalt.clikt.core.CliktCommand
import kotlin.random.Random
import kotlin.system.getTimeMillis
import kotlin.system.measureTimeMillis

const val EMOJI_M_SWORD = "⚔️"
const val EMOJI_M_SHIELD = "🛡️"
const val EMOJI_F_STAFF = "🪄"
const val EMOJI_F_KNIFE = "🔪"
const val UPDOWN_ARROW = "↕"
const val LEFT_RIGHT_ARROW = "⟺"

@Suppress("ClassName")
class Phase5_Omega : CliktCommand(help = "Phase 5 - Omega", name = "p5") {
    override fun run() {
        echo("Phase 5 - Omega")

        val seed = getTimeMillis()
        val random = Random(seed)
        println("Random seed -> $seed")
        while (true) {
            val isRight = print(
                listOf(EMOJI_F_KNIFE, EMOJI_F_STAFF).random(random),
                listOf(EMOJI_M_SWORD, EMOJI_M_SHIELD).random(random),
                listOf(LEFT_RIGHT_ARROW, UPDOWN_ARROW).random(random)
            )
            echo("정답? $isRight")
        }
    }

    private fun print(firstLandmark: String, secondLandmark: String, centerLandmark: String): Boolean {
        //1 ->TOP-LEFT First / 2 -> TOP-RIGHT First
        val randomType: Int = listOf(1, 2).random(Random(getTimeMillis()))

        echo("        A        ")
        echo("   4         1   ")
        if (randomType == 1) {
            echo("     $firstLandmark           ")
        } else {
            echo("          $firstLandmark      ")
        }
        echo("D       $centerLandmark       B")
        if (randomType == 1) {
            echo("          $secondLandmark      ")
        } else {
            echo("     $secondLandmark           ")
        }
        echo("   3         2   ")
        echo("        C        ")

        val answer: String = if (randomType == 1) {
            if (centerLandmark == LEFT_RIGHT_ARROW) {
                when {
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SWORD -> "c3"
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SHIELD -> "c2"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SWORD -> "a1"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SHIELD -> "c1"
                    else -> "??"
                }
            } else {
                when {
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SWORD -> "b3"
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SHIELD -> "b2"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SWORD -> "d1"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SHIELD -> "b1"
                    else -> "??"
                }
            }
        } else {
            if (centerLandmark == LEFT_RIGHT_ARROW) {
                when {
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SWORD -> "c3"
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SHIELD -> "c2"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SWORD -> "a1"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SHIELD -> "c1"
                    else -> "??"
                }
            } else {
                when {
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SWORD -> "d3"
                    firstLandmark == EMOJI_F_STAFF && secondLandmark == EMOJI_M_SHIELD -> "d2"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SWORD -> "b1"
                    firstLandmark == EMOJI_F_KNIFE && secondLandmark == EMOJI_M_SHIELD -> "d1"
                    else -> "??"
                }
            }
        }

        echo("가야될 위치는? ", false)

        lateinit var userAns: String

        val elapsedTime = measureTimeMillis {
            userAns = readln()
        }

        echo("Answer: $answer, ${elapsedTime / 1000.0}초 걸렸습니다.")
        return answer == userAns
    }
}