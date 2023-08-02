package br.com.leuras.core.business.pipeline

class Pipeline {

    private val steps = mutableListOf<PipelineStep>()

    fun addStep(step: PipelineStep): Pipeline {
        this.steps.add(step)

        return this
    }

    fun run(input: Any): Any? {
        var result: Any? = null

        this.steps.forEach {
            result = it.execute(result ?: input)
        }

        return result
    }
}