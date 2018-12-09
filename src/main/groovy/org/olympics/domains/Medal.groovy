package org.olympics.domains

/**
 * Medal of Gold, Silver or Bronze
 * Created by C. Leuer
 */
enum Medal {

  Gold('Gold'), Silver ('Silver'), Bronze('Bronze')

  String value

  Medal(String value) {
    this.value = value
  }

}