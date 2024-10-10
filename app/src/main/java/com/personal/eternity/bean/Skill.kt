package com.personal.eternity.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class Skill(
    @Id var id: Long = 0,
    val name: String = "",
    var hurt: Int = 0,
    var type: Int = -1,
    var addition: Float = 1f,
) {

    override fun toString(): String {
        return "Skill(id=$id, name='$name', hurt=$hurt)"
    }

}
