@file:Suppress("unused")

import xyz.jpenilla.runtask.pluginsapi.DownloadPluginsSpec

fun DownloadPluginsSpec.worldEdit(version: String = "7.3.15-beta-02") = hangar("WorldEdit", version)

fun DownloadPluginsSpec.worldGuard(version: String = "7.0.14") = modrinth("WorldGuard", version)

fun DownloadPluginsSpec.protocolLib(version: String = "5.3.0") {
    if (version == "dev") {
        url("https://ci.dmulloy2.net/job/ProtocolLib/lastSuccessfulBuild/artifact/build/libs/ProtocolLib.jar")
    } else {
        github("dmulloy2", "ProtocolLib", version, "ProtocolLib.jar")
    }
}

fun DownloadPluginsSpec.essentialsX(version: String = "2.21.1") {
    hangar("EssentialsX", version)
}

fun DownloadPluginsSpec.vault(version: String = "1.7.3") {
    github("MilkBowl", "Vault", version, "Vault.jar")
}

fun DownloadPluginsSpec.placeholderApi(version: String = "2.11.6") {
    hangar("PlaceholderAPI", version)
}

fun DownloadPluginsSpec.luckPerms(version: String = "5.5.0") {
    modrinth("LuckPerms", "v$version-bukkit")
}

fun DownloadPluginsSpec.itemNbtApi(version: String = "2.15.1") {
    modrinth("NBT-API", version)
}
