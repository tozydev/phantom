# Changelog

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
