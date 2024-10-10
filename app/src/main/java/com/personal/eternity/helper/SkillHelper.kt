package com.personal.eternity.helper

import com.personal.eternity.App
import com.personal.eternity.bean.MyObjectBox
import com.personal.eternity.bean.Skill
import com.personal.eternity.bean.SkillType
import com.personal.eternity.bean.Skill_
import com.personal.utils.Utils
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class SkillHelper {

    companion object {

        val get by lazy {
            SkillHelper()
        }

        val INSTANCE: BoxStore by lazy {
            MyObjectBox.builder()
                .androidContext(Utils.getApp())
                .build();
        }
    }


    fun putSkill(skill: Skill) {
        val boxFor = INSTANCE.boxFor<Skill>()
        val findFirst = boxFor.query(Skill_.name.equal(skill.name)).build().findFirst()
        findFirst?.also {
            skill.id = it.id
        }
        boxFor.put(skill)

    }


    fun putSkill(skills: Collection<Skill>) {
        INSTANCE.boxFor<Skill>().put(skills)
    }


    fun getSkill(): Skill? {
        return INSTANCE.boxFor<Skill>().query().build().findFirst()
    }


    fun putNeiGong(skills: MutableList<Skill>) {
        skills.forEach {
            it.type = SkillType.NEIGONG.type
            it.hurt = -1
        }
        INSTANCE.boxFor<Skill>().put(skills)

    }
}