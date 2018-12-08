package org.olympics.domains

/**
 * Medal of Gold, Silver or Bronze
 * Created by cjl7959 on 12/6/18.
 */
enum Medal {

  Gold('Gold'), Silver ('Silver'), Bronze('Bronze')

  String value

  Medal(String value) {
    this.value = value
  }

}