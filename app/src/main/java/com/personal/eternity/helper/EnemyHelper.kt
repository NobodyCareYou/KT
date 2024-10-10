package com.personal.eternity.helper

import com.personal.eternity.bean.PersonInfo
import kotlin.random.Random

class EnemyHelper {

    companion object {


        fun generatePerson(): List<PersonInfo> {
            val num = Random.nextInt(1, 5)
            val list = mutableListOf<PersonInfo>()
            for (i in 0 until num) {
                list.add(randomPerson())
            }
            return list
        }


        private fun randomPerson(): PersonInfo {
            return PersonInfo().apply {
                hp = Random.nextInt(50, 100)
                name = "杂役"
            }
        }

    }


}