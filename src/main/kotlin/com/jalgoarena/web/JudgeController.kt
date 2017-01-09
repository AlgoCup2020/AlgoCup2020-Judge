package com.jalgoarena.web

import com.jalgoarena.data.DataRepository
import com.jalgoarena.judge.JudgeEngine
import com.jalgoarena.domain.JudgeResult
import com.jalgoarena.domain.Problem
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@CrossOrigin
@RestController
class JudgeController(
        @Inject val problemsClient: DataRepository<Problem>,
        @Inject var judgeEngine: JudgeEngine) {

    @PostMapping("/problems/{id}/submit", produces = arrayOf("application/json"))
    fun judge(@PathVariable id: String, @RequestBody sourceCode: String): JudgeResult {
        val problem = problemsClient.find(id) ?:
                return JudgeResult.RuntimeError("Wrong problem id: $id")

        return judgeEngine.judge(problem, sourceCode)
    }
}
