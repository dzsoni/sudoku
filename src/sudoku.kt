@file:JvmName("Sudoku")

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import sudoku.SudokuTable
import java.io.BufferedReader
import java.io.File

//--------------------------------------------------
const  val VERSION_MAJOR: Int = 0
const  val VERSION_MINOR: Int = 1

val NEW_LINE = System.getProperty("line.separator")
//--------------------------------------------------

fun main(args: Array<String>) {

    var list :List<String>? = emptyList()
    var sT: SudokuTable = SudokuTable()

    //Apache Commons CLI
    var options = Options()
    var parser = DefaultParser()

    //Make an option -f --file with "file name" argument
    options.addOption(Option.builder("f")
            .longOpt("file")
            .desc("The name of Sudoku table file.")
            .hasArg(true)
            .argName("file name")
            .build())
    //Make an option -h --help Help.
    options.addOption(Option.builder("h")
            .longOpt("help")
            .desc("Help.")
            .build())


    var cmd = parser.parse(options, args)
    if(cmd.hasOption("h")){
        var formater = HelpFormatter()
        formater.printHelp("sudoku.jar","Sodoku solver",options,"",true)
        System.exit(0)
    }

    if (cmd.hasOption("f")) {
        var fileR: BufferedReader? = null
        try {

            fileR = File(cmd.getOptionValue("f")).bufferedReader()
            // Read the first 9 lines
            list = fileR.useLines { lines: Sequence<String> -> lines.take(9).toList() }

        } catch (e: Exception) {
            println("Error while reading sudoku file.")
            if (fileR != null) {
                fileR.close()
            }
            throw Exception ()
        }

        if (list.firstOrNull() == null) {
            throw Exception(NEW_LINE+"Üres sudoku tábla."+ NEW_LINE)
        }
        if (list.size != 9) {
            throw Exception(NEW_LINE+"Korrupt sudoku tábla.A sorok száma kevés."+NEW_LINE)
        }


        list.forEachIndexed { row, s ->
            s.splitToSequence(",", limit = 9).forEachIndexed { col, c ->
                sT.table[row][col].setValue(c.toInt())
            }
        }

    } else {
        throw Exception(NEW_LINE+"Adj meg egy file nevet."+ NEW_LINE)
    }


    //print sudoku table
    for (r in 0..8) {
        for (c in 0..8) {
            print(sT.table[r][c].getValue().toString())
        }
        print("\n")
    }

}