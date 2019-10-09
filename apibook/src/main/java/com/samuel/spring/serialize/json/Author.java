package com.samuel.spring.serialize.json;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "author_nom",
    "author_prenom"
})
public class Author {

    @JsonProperty("author_nom")
    private String authorNom;
    @JsonProperty("author_prenom")
    private String authorPrenom;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("author_nom")
    public String getAuthorNom() {
        return authorNom;
    }

    @JsonProperty("author_nom")
    public void setAuthorNom(String authorNom) {
        this.authorNom = authorNom;
    }

    @JsonProperty("author_prenom")
    public String getAuthorPrenom() {
        return authorPrenom;
    }

    @JsonProperty("author_prenom")
    public void setAuthorPrenom(String authorPrenom) {
        this.authorPrenom = authorPrenom;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
