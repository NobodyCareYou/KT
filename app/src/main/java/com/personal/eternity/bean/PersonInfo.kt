package com.personal.eternity.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class PersonInfo(
    @Id var id: Long = 0,
    var name: String = "张三",
    var hp: Int = 100,
    var mp: Int = 0,
    var skillIds: Array<String> = emptyArray<String>(),
    var desc: String = "战斗力为5的渣渣"
) {
    override fun toString(): String {
        return "PersonInfo(id=$id, name='$name', hp=$hp, mp=$mp, skillIds=${skillIds.contentToString()}, desc='$desc')"
    }
}