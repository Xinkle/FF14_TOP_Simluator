import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class FF14TOPSimulator : CliktCommand() {
    override val commandHelp: String
        get() = """
            만든이:리슈넨트@톤베리(Xinkle)
        """.trimIndent()

    override fun run() = Unit
}


fun IntArray.swap(first: Int, second: Int) {
    val temp = get(first)

    this[first] = get(second)
    this[second] = temp
}

fun main(args: Array<String>) = FF14TOPSimulator()
    .subcommands(Phase2_PartySynergy())
    .subcommands(Phase5_Omega())
    .main(args)