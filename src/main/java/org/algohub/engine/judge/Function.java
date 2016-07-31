package org.algohub.engine.judge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.algohub.engine.type.TypeNode;
import org.algohub.engine.type.TypeNodeDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Function {

    private final String name;
    /**
     * Return metadata.
     */
    @JsonProperty("return")
    private final Return returnStatement;
    /**
     * Parameters' metadata.
     */
    private final Parameter[] parameters;

    /**
     * Since this class is immutable, need to provide a method for Jackson.
     */
    @JsonCreator
    public Function(@JsonProperty("name") final String name,
                    @JsonProperty("return") final Return returnStatement,
                    @JsonProperty("parameters") final Parameter[] parameters) {
        this.name = name;
        this.returnStatement = returnStatement;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public Return getReturnStatement() {
        return returnStatement;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    /**
     * Return type.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Return {
        /**
         * Return data type.
         */
        @JsonDeserialize(using = TypeNodeDeserializer.class)
        private final TypeNode type;
        /**
         * Comment of returned value.
         */
        private final String comment;

        /**
         * Since this class is immutable, need to provide a method for Jackson.
         */
        @JsonCreator
        public Return(@JsonProperty("type") final TypeNode type,
                      @JsonProperty("comment") final String comment) {
            this.type = type;
            this.comment = comment;
        }

        public TypeNode getType() {
            return type;
        }

        public String getComment() {
            return comment;
        }
    }

    /**
     * Function parameters' metadata.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        /**
         * Parameter name.
         */
        private final String name;
        /**
         * Parameter type.
         */
        @JsonDeserialize(using = TypeNodeDeserializer.class)
        private final TypeNode type;
        /**
         * Parameter comment.
         */
        private final String comment;

        /**
         * Since this class is immutable, need to provide a method for Jackson.
         */
        @JsonCreator
        public Parameter(@JsonProperty("name") final String name,
                         @JsonProperty("type") final TypeNode type, @JsonProperty("comment") final String comment) {
            this.name = name;
            this.type = type;
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public TypeNode getType() {
            return type;
        }

        public String getComment() {
            return comment;
        }
    }
}
