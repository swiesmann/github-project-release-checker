package github

import khttp.responses.Response
import org.json.JSONObject
import java.net.URL
import khttp.get as httpGet

class GithubService {

    fun getLatestRelease(projectUrl: URL): JSONObject {
        println(projectUrl)

        if ("github.com" != projectUrl.host) {
            throw IllegalArgumentException("%s is not github.com".format(projectUrl.host))
        }

        val repo: GithubRepository = extractRepoInfosFromUrl(projectUrl)
        val latestRelease = fetchLatestRelease(repo)

        return latestRelease.jsonObject
    }

    private fun fetchLatestRelease(repo: GithubRepository): Response {
        return httpGet(
            LATEST_RELEASE_URL_TEMPLATE.format(repo.owner, repo.name),
            headers = mapOf("Accept" to API_ACCEPT_HEADER)
        )
    }

    private fun extractRepoInfosFromUrl(projectUrl: URL): GithubRepository {
        val urlPathParts = projectUrl.path.split('/')
        return GithubRepository(urlPathParts[2], urlPathParts[1])
    }

    companion object {
        private const val LATEST_RELEASE_URL_TEMPLATE = "https://api.github.com/repos/%s/%s/releases/latest"
        private const val API_ACCEPT_HEADER = "application/vnd.github.v3+json"
    }

    data class GithubRepository(val name: String, val owner: String)
    data class GithubRelease(
        val name: String,
        val html_url: URL,
        val draft: Boolean,
        val prerelease: Boolean
    )
}