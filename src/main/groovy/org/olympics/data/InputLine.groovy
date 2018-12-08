package org.olympics.data

import com.xlson.groovycsv.CsvParser
import groovy.transform.ToString
import groovy.util.logging.Slf4j

/**
 * An input line of olympics results
 * Created by cjl7959 on 12/6/18.
 *
 * ATTRIBUTION: Library https://github.com/xlson/groovycsv/
 * to parse csv lines. Author: Leonard Gram.
 */

@Slf4j
@ToString(includeNames = true)
class InputLine {

  private static final String NOT_APPLICABLE = 'NA'

  Long id
  String name
  String sex
  Integer age
  Integer height
  BigDecimal weight
  String team
  String noc
  String gameName
  Integer year
  String season
  String city
  String sport
  String event
  String medal

  InputLine (String line) {
    def data = CsvParser.parseCsv(line, readFirstLine:true,
        columnNames:['ID', 'name', 'sex', 'age', 'height', 'weight', 'team', 'noc', 'game', 'year', 'season', 'city', 'sport', 'event', 'medal'])[0]
    id = replaceNa(data.ID) as Long
    name = replaceNa(data.name)
    sex = replaceNa(data.sex)
    age = replaceNa(data.age) as Integer
    height = replaceNa(data.height) as Integer
    weight = replaceNa(data.weight) as BigDecimal
    team = replaceNa(data.team)
    noc = replaceNa(data.noc)
    gameName = replaceNa(data.game)
    year = replaceNa(data.year) as Integer
    season = replaceNa(data.season)
    city = replaceNa(data.city)
    sport = replaceNa(data.sport)
    event = replaceNa(data.event)
    medal = replaceNa(data.medal)
  }

  private static String replaceNa (String field) {
    NOT_APPLICABLE == field ? null : field
  }

}
