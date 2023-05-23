import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class FF14TOPSimulator : CliktCommand() {
    override fun run() = Unit
}

class Phase2PartySynergy : CliktCommand(help = "Phase 2 - Party Synergy", name = "p2") {
    override fun run() {
        echo("Phase 2 - Party Synergy")
        echo("공략방식 - 누케마루")
        echo("▽")
        echo("▢")
        echo("◯")
        echo("⨯")

//        val name = readln()
//        echo("\r[$name]")
    }
}

fun main(args: Array<String>) = FF14TOPSimulator()
    .subcommands(Phase2PartySynergy())
    .main(args)