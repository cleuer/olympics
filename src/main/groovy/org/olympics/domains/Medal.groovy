package org.olympics.domains

/**
 * Medal of Gold, Silver or Bronze
 * todo: remove me.  unfortunately, Spring Data Neo4J does not support saving enums to database
 *  OGM library does not suppor @Enumerated(EnumType.STRING)
 * Created by C. Leuer
 */
enum Medal {

  Gold('Gold'), Silver ('Silver'), Bronze('Bronze')

  String value

  Medal(String value) {
    this.value = value
  }

}