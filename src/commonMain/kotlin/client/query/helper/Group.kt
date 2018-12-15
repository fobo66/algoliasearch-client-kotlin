package client.query.helper


sealed class Group(open val name: String) {

    internal data class Key(val name: String, val key: FilterKey)
}

data class GroupAnd(override val name: String) : Group(name)

data class GroupOr(override val name: String) : Group(name)