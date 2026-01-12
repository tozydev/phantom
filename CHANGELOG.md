# Changelog

## [0.10.0](https://github.com/tozydev/phantom/compare/v0.9.2...v0.10.0) (2026-01-12)


### ⚠ BREAKING CHANGES

* **infra:** migrate host repository to tozydev Maven Repository

### Bug Fixes

* **deps:** update dependency com.palantir.javapoet:javapoet to v0.10.0 ([#117](https://github.com/tozydev/phantom/issues/117)) ([e7a8ae1](https://github.com/tozydev/phantom/commit/e7a8ae1d0300bb92c04ce328df4fd0e736668420))
* **deps:** update dependency org.mariadb.jdbc:mariadb-java-client to v3.5.7 ([#112](https://github.com/tozydev/phantom/issues/112)) ([744f876](https://github.com/tozydev/phantom/commit/744f876d78417909fc0f49885036e24c47b3bfe3))
* **deps:** update invui to v2.0.0-alpha.26 ([#109](https://github.com/tozydev/phantom/issues/109)) ([3e6b921](https://github.com/tozydev/phantom/commit/3e6b921ccbee38ccdaa5f4e7bc5c6d1117b5e26c))
* **deps:** update plugin-libs ([#110](https://github.com/tozydev/phantom/issues/110)) ([de6f920](https://github.com/tozydev/phantom/commit/de6f920ed00c3cdaaf701a86cfcf6f6110a88e5c))
* **deps:** update plugin-libs ([#115](https://github.com/tozydev/phantom/issues/115)) ([5f3673e](https://github.com/tozydev/phantom/commit/5f3673ee137c9a1c0feb30eb35e7e0460ca07fa5))


### Code Refactoring

* **infra:** migrate host repository to tozydev Maven Repository ([645570c](https://github.com/tozydev/phantom/commit/645570cf1ce0b7c81599b92175948afecccdabda))

## [0.9.2](https://github.com/tozydev/phantom/compare/v0.9.1...v0.9.2) (2025-12-06)


### Bug Fixes

* change plugin load order to startup ([f41de25](https://github.com/tozydev/phantom/commit/f41de25676dc233a2e101e3e5ad70a01cc97784d))
* **deps:** update dependency com.palantir.javapoet:javapoet to v0.9.0 ([#103](https://github.com/tozydev/phantom/issues/103)) ([def99d3](https://github.com/tozydev/phantom/commit/def99d35c2f8344c9781b32b40f484a434436874))
* **deps:** update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-rc-4 ([#93](https://github.com/tozydev/phantom/issues/93)) ([0226cf3](https://github.com/tozydev/phantom/commit/0226cf334f28cd23da3e2fe2e2aa4024e51f199a))
* **deps:** update dependency org.xerial:sqlite-jdbc to v3.51.1.0 ([#105](https://github.com/tozydev/phantom/issues/105)) ([7b0303a](https://github.com/tozydev/phantom/commit/7b0303a581373e21084ace6bcc5193970794c315))
* isolate the eco plugin classpath because of conflict ([5c666d3](https://github.com/tozydev/phantom/commit/5c666d3941ca3eb7a488b445cdbc0e5147855156))


### Reverts

* "fix(deps): update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-rc-4 ([#93](https://github.com/tozydev/phantom/issues/93))" ([0456a0b](https://github.com/tozydev/phantom/commit/0456a0b98be4d129b6d0ce2e281a0223cae15c05))

## [0.9.1](https://github.com/tozydev/phantom/compare/v0.9.0...v0.9.1) (2025-11-19)


### Bug Fixes

* **deps:** update dependency com.palantir.javapoet:javapoet to v0.8.0 ([#96](https://github.com/tozydev/phantom/issues/96)) ([8ce008d](https://github.com/tozydev/phantom/commit/8ce008d3fbff839a0b70fb4f3c29a63d37c316f9))
* **deps:** update invui to v2.0.0-alpha.21 ([#98](https://github.com/tozydev/phantom/issues/98)) ([6354d3a](https://github.com/tozydev/phantom/commit/6354d3ac6e09d9e9ebdaa31a5a7d39e9b8386391))
* **deps:** update plugin-libs ([#91](https://github.com/tozydev/phantom/issues/91)) ([653b690](https://github.com/tozydev/phantom/commit/653b690d9b1fa1362434333cf376a90f9086c269))

## [0.9.0](https://github.com/tozydev/phantom/compare/v0.8.0...v0.9.0) (2025-10-27)


### ⚠ BREAKING CHANGES

* **deps:** update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-rc-2 ([#85](https://github.com/tozydev/phantom/issues/85))
* **deps:** update invui to v2.0.0-alpha.20 ([#76](https://github.com/tozydev/phantom/issues/76))

### Bug Fixes

* **deps:** update dependency com.h2database:h2 to v2.4.240 ([#79](https://github.com/tozydev/phantom/issues/79)) [skip ci] ([e35a41e](https://github.com/tozydev/phantom/commit/e35a41e226c07d7cb49eda6aac9d1db315919c0c))
* **deps:** update dependency de.tr7zw:item-nbt-api-plugin to v2.15.3 ([#84](https://github.com/tozydev/phantom/issues/84)) ([aa7fe59](https://github.com/tozydev/phantom/commit/aa7fe59ff223b34c066cc5f9d8792886df1594a6))
* **deps:** update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-rc-2 ([#85](https://github.com/tozydev/phantom/issues/85)) ([80ef602](https://github.com/tozydev/phantom/commit/80ef602291ac8dd22616fc6b30e6920ef41bc2be))
* **deps:** update dependency org.mariadb.jdbc:mariadb-java-client to v3.5.6 ([#72](https://github.com/tozydev/phantom/issues/72)) ([51fc5f4](https://github.com/tozydev/phantom/commit/51fc5f4704aee5ceb1b0700761056093e7c92a7d))
* **deps:** update dependency org.postgresql:postgresql to v42.7.8 ([#75](https://github.com/tozydev/phantom/issues/75)) ([1dffc28](https://github.com/tozydev/phantom/commit/1dffc2801fd976c3d33e36505fbb431deee08d6d))
* **deps:** update invui to v2.0.0-alpha.20 ([#76](https://github.com/tozydev/phantom/issues/76)) ([05a1b1b](https://github.com/tozydev/phantom/commit/05a1b1bce934581a740ab0a55dfadf8d06315554))
* **deps:** update plugin-libs ([#88](https://github.com/tozydev/phantom/issues/88)) ([d786501](https://github.com/tozydev/phantom/commit/d786501ef264cceae3190d528240fe43384eb122))

## [0.8.0](https://github.com/tozydev/phantom/compare/v0.7.1...v0.8.0) (2025-09-05)


### Features

* **paper-core:** add methods to retrieve and execute actions with ingredients in GuiConfig ([939ef32](https://github.com/tozydev/phantom/commit/939ef3203fd293f918fde8da9caf44f2d904f557))


### Reverts

* "fix(paper-core): update ingredients map to use Char as key in GuiConfig" ([32e4989](https://github.com/tozydev/phantom/commit/32e49893a41ca85afca20044c09c421021513ef5))

## [0.7.1](https://github.com/tozydev/phantom/compare/v0.7.0...v0.7.1) (2025-09-05)


### Bug Fixes

* **paper-core:** add old-style placeholder builder functions to keep compatibility ([cc48c86](https://github.com/tozydev/phantom/commit/cc48c8620d943ba1f940cbdb89f8ac6a91f83656))

## [0.7.0](https://github.com/tozydev/phantom/compare/v0.6.3...v0.7.0) (2025-09-05)


### Features

* **core-paper:** add pluginCommand extension for CommandAPI registration ([ef06bc6](https://github.com/tozydev/phantom/commit/ef06bc6e112bb3f2205367c92d9158a0c179dae6))
* **paper-core:** add operator function for TagResolver to handle custom tags ([8aa8bf3](https://github.com/tozydev/phantom/commit/8aa8bf378a4bcbdebac99ade1527f33d0848e643))
* **paper-core:** add sendRichMessage function with tagResolver lambda ([83233eb](https://github.com/tozydev/phantom/commit/83233ebce12016583c4b0e7f506d482a6b4bce5c))
* **paper-core:** enhance TagResolver with new placeholder and formatting extensions function ([fcb4d50](https://github.com/tozydev/phantom/commit/fcb4d50ef30d5c98d4a7419d3c58a0d6345b83f9))


### Bug Fixes

* **deps:** update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-rc-1 ([#62](https://github.com/tozydev/phantom/issues/62)) ([182017b](https://github.com/tozydev/phantom/commit/182017b6d91ce97971eb6840e6280b6f59d8a02d))
* **deps:** update plugin-libs ([#59](https://github.com/tozydev/phantom/issues/59)) ([510a0a2](https://github.com/tozydev/phantom/commit/510a0a23505ebeb2e2b56338fae8ccc4a47da060))
* **paper-core:** handle null default node in GuiItem serialization ([b507607](https://github.com/tozydev/phantom/commit/b507607fe61393daed5a30cf437cf6c7d8b04dfb))
* **paper-core:** update ingredients map to use Char as key in GuiConfig ([99c1fb4](https://github.com/tozydev/phantom/commit/99c1fb4be60c1f3cecc678f339d608c13c15b707))


### Documentation

* **paper-core:** add docs for TagResolvers extensions ([7576723](https://github.com/tozydev/phantom/commit/75767234e2ab5346e4afc058380fac91e7c3cace))

## [0.6.3](https://github.com/tozydev/phantom/compare/v0.6.2...v0.6.3) (2025-08-25)


### Bug Fixes

* **paper-core:** missing required serializer in item builder serialization ([476d8ce](https://github.com/tozydev/phantom/commit/476d8ce0405b29b760417dd72504f78d84e309e5))
* **paper-core:** name and lore always italic in item stack builder ([0c7522e](https://github.com/tozydev/phantom/commit/0c7522e19165847643341545f24df294ca68591f))

## [0.6.2](https://github.com/tozydev/phantom/compare/v0.6.1...v0.6.2) (2025-08-25)


### Bug Fixes

* **paper-core:** wrong gui ingredients type ([b67827c](https://github.com/tozydev/phantom/commit/b67827cbf3c4f4ce43eabb5ce6832c162b6e6295))

## [0.6.1](https://github.com/tozydev/phantom/compare/v0.6.0...v0.6.1) (2025-08-25)


### Bug Fixes

* **deps:** update dependency com.zaxxer:hikaricp to v7.0.2 ([#50](https://github.com/tozydev/phantom/issues/50)) ([2207afc](https://github.com/tozydev/phantom/commit/2207afcf113cb1fa83bd46005d65a8aa3c05574c))
* **deps:** update invui to v2.0.0-alpha.18 ([#51](https://github.com/tozydev/phantom/issues/51)) ([36850d2](https://github.com/tozydev/phantom/commit/36850d22a4cdb94a61829f344222007c92e36227))

## [0.6.0](https://github.com/tozydev/phantom/compare/v0.5.0...v0.6.0) (2025-08-15)


### Features

* **core:** add TagResolver and placeholder utility functions ([68a3b2f](https://github.com/tozydev/phantom/commit/68a3b2f378be88648b3ecac4e3eee646358e4283))
* **gradle-plugin:** add PacketEvents Spigot dependency extension ([0437a4f](https://github.com/tozydev/phantom/commit/0437a4f4900df0313f7727e0914705de0218f978))
* **gradle-plugin:** add Phantom repository injection logic when apply phantom libraries ([d60792b](https://github.com/tozydev/phantom/commit/d60792bcb856448800c83d36ff7f04802b86fa72))


### Bug Fixes

* **deps:** update dependency com.zaxxer:hikaricp to v7.0.1 ([#42](https://github.com/tozydev/phantom/issues/42)) ([27c3970](https://github.com/tozydev/phantom/commit/27c3970fc3d5cb52eb6a217e8f4052f2745e50ec))
* **deps:** update dependency org.mariadb.jdbc:mariadb-java-client to v3.5.5 ([#44](https://github.com/tozydev/phantom/issues/44)) ([27c757e](https://github.com/tozydev/phantom/commit/27c757e16abee20fb997e1b7a03321ccefc6fec1))
* **deps:** update invui to v2.0.0-alpha.17 ([#43](https://github.com/tozydev/phantom/issues/43)) ([a1c03e4](https://github.com/tozydev/phantom/commit/a1c03e4956159039463739a2ca0af6c0ce0c714e))
* **gradle-plugin:** change EssentialsX plugin download to modrinth ([a7cf2e8](https://github.com/tozydev/phantom/commit/a7cf2e87cdd3cef292f80aec61bff14a4ac1070d))
* **gradle-plugin:** update plugin versions for WorldEdit, ProtocolLib, and EssentialsX ([124362b](https://github.com/tozydev/phantom/commit/124362b456600791e6b839a85fb236ac786fdd54))
* **gradle-plugin:** update ProtocolLib version to 5.4.0 ([68bc9ab](https://github.com/tozydev/phantom/commit/68bc9ab44dfd440b75d35bf67746c7ef4e022a9f))

## [0.5.0](https://github.com/tozydev/phantom/compare/v0.4.1...v0.5.0) (2025-08-05)


### Features

* **gradle-plugin:** add phantomPaperPlugin function to download Phantom Paper plugin ([ff8c616](https://github.com/tozydev/phantom/commit/ff8c616007c65714515cf28691cd7ba8e4bbd2ea))

## [0.4.1](https://github.com/tozydev/phantom/compare/v0.4.0...v0.4.1) (2025-08-04)


### Bug Fixes

* **paper-core:** update MessageNodeParser to use virtual node checks for message parsing ([6f98bb6](https://github.com/tozydev/phantom/commit/6f98bb6b0479318300bcc8cab293294b9cabbd46))

## [0.4.0](https://github.com/tozydev/phantom/compare/v0.3.0...v0.4.0) (2025-08-03)


### ⚠ BREAKING CHANGES

* **gradle-plugin:** require Gradle 9.0.0 to apply plugin

### Features

* add invui libraries ([0454e24](https://github.com/tozydev/phantom/commit/0454e2469d347c714b260ee8c96d96ad74623f9e))
* **gradle-plugin:** add extensions to filter library loader repositories ([81026a9](https://github.com/tozydev/phantom/commit/81026a9a82cb8e555c550f8491fc64878513f419))
* **gradle-plugin:** require Gradle 9.0.0 to apply plugin ([08bd108](https://github.com/tozydev/phantom/commit/08bd10886736fca258e0943e5077ae2a7e413a58))
* **paper-core:** add BukkitColorSerializer for Color deserialization and serialization ([5b4ca33](https://github.com/tozydev/phantom/commit/5b4ca339303d8baa275b1e0608cceffaf8c1404f))
* **paper-core:** add configuration serializers for enhanced configuration handling ([9849f68](https://github.com/tozydev/phantom/commit/9849f68e84ef080d1b065cc5f495db447b6c7773))
* **paper-core:** add GuiConfig and GuiItem classes for GUI configuration and serialization ([b329f46](https://github.com/tozydev/phantom/commit/b329f462527a0e920ad3f135edf424ab3e9195da))
* **paper-core:** add ItemStackBuilder for flexible ItemStack creation and serialization ([a6ac969](https://github.com/tozydev/phantom/commit/a6ac96954c38277d9a26ea1eee8989b5927ca061))
* **paper-core:** add NbtTag data class and serializer for NBT handling ([a2dc9a4](https://github.com/tozydev/phantom/commit/a2dc9a4755215021fc3629b6f99d4f86d4781548))
* **paper-core:** add player chat input prompts feature ([3459313](https://github.com/tozydev/phantom/commit/34593134a4c98bc3d780e7925cdf0fd7fa52c19b))
* **paper-core:** add serialization functions for Component to plain and legacy text ([3a79b93](https://github.com/tozydev/phantom/commit/3a79b938c52ef00976b6ba01e084a19d87d75a59))
* **paper-core:** add UuidTagType for UUID persistence ([b88ab10](https://github.com/tozydev/phantom/commit/b88ab10550c131ff01a8607dac9f033f790c20ad))
* update minecraft version 1.21.8 ([91f32c1](https://github.com/tozydev/phantom/commit/91f32c1dcefdc7450a76bec33d919a47fbb1bf57))


### Bug Fixes

* **dep-libs:** update item-nbt-api module reference to item-nbt-api-plugin ([b800a11](https://github.com/tozydev/phantom/commit/b800a111d8b4fd27b5fd8b0296d7a0cf5e71287c))
* **deps:** update dependency com.mysql:mysql-connector-j to v9.4.0 ([#31](https://github.com/tozydev/phantom/issues/31)) ([f95b08e](https://github.com/tozydev/phantom/commit/f95b08ee103d83caa055b75ca2734ec83cea0c39))
* **deps:** update dependency org.jetbrains.exposed:exposed-bom to v1.0.0-beta-5 ([#30](https://github.com/tozydev/phantom/issues/30)) ([15a0294](https://github.com/tozydev/phantom/commit/15a02947c7b984b6f6ad91021298e405cf296fc4))
* **gradle-plugin:** correct modrinth reference for NBTAPI in DownloadPluginsSpecExtensions ([856ed90](https://github.com/tozydev/phantom/commit/856ed90455d3128130fe614a84fad5d6a6e04b28))


### Reverts

* d895337f ([53aa5b7](https://github.com/tozydev/phantom/commit/53aa5b703578090e078073e16990d027f674f6e1))
* **gradle-plugin:** add library repositories filter functionality ([53aa5b7](https://github.com/tozydev/phantom/commit/53aa5b703578090e078073e16990d027f674f6e1))

## [0.3.0](https://github.com/tozydev/phantom/compare/v0.2.2...v0.3.0) (2025-07-29)


### Features

* add KotlinDurationSerializer for Kotlin's Duration type ([f12b51e](https://github.com/tozydev/phantom/commit/f12b51e3c3eb104d1409a4e3651390f85d931fc9))
* add local publishing repository configuration ([bdd3cfc](https://github.com/tozydev/phantom/commit/bdd3cfcf9fffe0362b3d2af48d0c50a414d91c15))
* **gradle-plugin:** add library repositories filter functionality ([d895337](https://github.com/tozydev/phantom/commit/d895337f9b0ddfa469fa5fc3b6518db9b5d727e0))
* **paper-plugin:** add paper-plugin as library bundles ([425c4fe](https://github.com/tozydev/phantom/commit/425c4fe701ed1e9021b6d1201e7d01f269d268df))


### Bug Fixes

* **deps:** update commandapi to v10.1.2 ([#21](https://github.com/tozydev/phantom/issues/21)) ([c6e233a](https://github.com/tozydev/phantom/commit/c6e233a9653f643f857009619bcdbffb21e7c958))
* **deps:** update dependency com.zaxxer:hikaricp to v6.3.2 ([#22](https://github.com/tozydev/phantom/issues/22)) ([fd6aa74](https://github.com/tozydev/phantom/commit/fd6aa748825379563743d86bc4a3ff25bdb389a6))
* **deps:** update dependency com.zaxxer:hikaricp to v7 ([#25](https://github.com/tozydev/phantom/issues/25)) ([0f305ab](https://github.com/tozydev/phantom/commit/0f305ab91417636a94203d5fff28e51a3910dc44))
* **gradle-plugin:** relocator error ([9ab0e09](https://github.com/tozydev/phantom/commit/9ab0e09095e8f9f7f555c38afa88aafdcb25f33a))
* remove shadow configurator ([150f659](https://github.com/tozydev/phantom/commit/150f6595646c4fcfc6afa77d1b0f2848d1445972))
* rename databaseCore method to databaseJdbc in DependenciesExtensionConfigurer ([e4ebc1e](https://github.com/tozydev/phantom/commit/e4ebc1ea76016fb2df14cc75fc34c809cfc27634))

## [0.2.2](https://github.com/tozydev/phantom/compare/v0.2.1...v0.2.2) (2025-07-22)


### Bug Fixes

* remove abstract async lifecycle methods from PhantomPaperPlugin ([f406f82](https://github.com/tozydev/phantom/commit/f406f828d23a7fa12435351b4dc9eb63bcae1ba1))
* update dependency references in DependenciesExtensionConfigurer ([0de14aa](https://github.com/tozydev/phantom/commit/0de14aa115aacdc136248ee09f35d2722e796b3d))

## [0.2.1](https://github.com/tozydev/phantom/compare/v0.2.0...v0.2.1) (2025-07-22)


### Bug Fixes

* **core:** lowercase key string before deserialize ([f0420ed](https://github.com/tozydev/phantom/commit/f0420ed5208c059bd8851002512c7f81c8fbfbf2))
