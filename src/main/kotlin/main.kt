import github.GithubService
import java.io.File
import java.net.URL
import java.util.*
import java.util.stream.Collectors

const val DATA_DIR = "./.github-project-release-checker"
const val STORE_OUTPUT_COMMAND_TEMPLATE = "::set-output name=releases_as_json::%s"

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        print("no project URLs present. bye.")
        return
    }

    val dataDir = File(DATA_DIR)
    dataDir.mkdirs()

    val githubService = GithubService()
    val releases = Arrays.stream(args)
            .map { URL(it) }
            .map { githubService.getLatestRelease(it) }
            .collect(Collectors.toList())

    println(STORE_OUTPUT_COMMAND_TEMPLATE.format(releases.toString()))
}