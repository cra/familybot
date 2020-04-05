package space.yaroslav.familybot.models

import space.yaroslav.familybot.telegram.FamilyBot

enum class Phrase(val id: Int) {
    BAD_COMMAND_USAGE(1),
    ASK_WORLD_LIMIT_BY_CHAT(2),
    ASK_WORLD_LIMIT_BY_USER(3),
    ASK_WORLD_HELP(4),
    DATA_CONFIRM(5),
    ASK_WORLD_QUESTION_FROM_CHAT(6),
    STATS_BY_COMMAND(7),
    COMMAND(8),
    PLURALIZED_COUNT_ONE(9),
    PLURALIZED_COUNT_FEW(10),
    PLURALIZED_COUNT_MANY(11),
    PLURALIZED_MESSAGE_ONE(12),
    PLURALIZED_MESSAGE_FEW(13),
    PLURALIZED_MESSAGE_MANY(14),
    PLURALIZED_LEADERBOARD_ONE(15),
    PLURALIZED_LEADERBOARD_FEW(16),
    PLURALIZED_LEADERBOARD_MANY(17),
    PIDOR_SEARCH_START(18),
    PIDOR_SEARCH_MIDDLE(19),
    PIDOR_SEARCH_FINISHER(20),
    YOU_TALKED(21),
    YOU_WAS_NOT_PIDOR(22),
    YOU_WAS_PIDOR(23),
    YOU_USED_COMMANDS(24),
    PIROR_DISCOVERED_MANY(25),
    PIROR_DISCOVERED_ONE(26),
    PIDOR_STAT_WORLD(27),
    PIDOR_STAT_MONTH(28),
    PIDOR_STAT_YEAR(29),
    PIDOR_STAT_ALL_TIME(30),
    RAGE_DONT_CARE_ABOUT_YOU(31),
    RAGE_INITIAL(32),
    ROULETTE_ALREADY_WAS(33),
    PIDOR(34),
    ROULETTE_MESSAGE(35),
    WHICH_SETTING_SHOULD_CHANGE(36),
    LEADERBOARD_TITLE(37),
    ACCESS_DENIED(38),
    STOP_DDOS(39),
    COMMAND_IS_OFF(40),
    PIDOR_COMPETITION(41),
    COMPETITION_ONE_MORE_PIDOR(42),
    HELP_MESSAGE(43),
    USER_ENTERING_CHAT(44),
    USER_LEAVING_CHAT(45),
    BET_INITIAL_MESSAGE(46),
    BET_ALREADY_WAS(47),
    BET_WIN(48),
    BET_LOSE(49),
    BET_ZATRAVOCHKA(50),
    BET_BREAKING_THE_RULES_FIRST(51),
    BET_BREAKING_THE_RULES_SECOND(52),
    BET_EXPLAIN(53),
    PLURALIZED_DAY_ONE(54),
    PLURALIZED_DAY_FEW(55),
    PLURALIZED_DAY_MANY(56),
    PLURALIZED_NEXT_ONE(57),
    PLURALIZED_NEXT_FEW(58),
    PLURALIZED_NEXT_MANY(59),
    PLURALIZED_OCHKO_ONE(60),
    PLURALIZED_OCHKO_FEW(61),
    PLURALIZED_OCHKO_MANY(62),
    PLURALIZED_PIDORSKOE_ONE(63),
    PLURALIZED_PIDORSKOE_FEW(64),
    PLURALIZED_PIDORSKOE_MANY(65),
    BET_EXPLAIN_SINGLE_DAY(66),
    BET_WIN_END(67),
    SUCHARA_HELLO_MESSAGE(68),
    ASK_WORLD_REPLY_FROM_CHAT(69),
    TECHNICAL_ISSUE(70),
    BET_WINNABLE_NUMBERS_ANNOUNCEMENT(71);

    companion object {
        private val lookupMap = values().map { it.id to it }.toMap()
        fun lookup(id: Int): Phrase {
            return lookupMap[id] ?: throw FamilyBot.InternalException("Wrong id $id was provided")
        }
        fun lookup(name: String): Phrase {
            return values().find { it.name == name } ?: throw FamilyBot.InternalException("Wrong name $name was provided")
        }
    }
}

enum class PhraseTheme(val id: Int) {
    DEFAULT(1),
    DAY_OF_DEFENDER_23_FEB(2),
    DAY_OF_WOMAN_8_MARCH(3);

    companion object {
        private val lookupMap = values().map { it.id to it }.toMap()
        fun lookup(id: Int): PhraseTheme {
            return lookupMap[id] ?: throw FamilyBot.InternalException("Wrong id $id was provided")
        }
        fun lookup(name: String): PhraseTheme {
            return values().find { it.name == name } ?: throw FamilyBot.InternalException("Wrong name $name was provided")
        }
    }
}