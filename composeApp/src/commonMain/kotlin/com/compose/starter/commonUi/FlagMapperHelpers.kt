package com.compose.starter.commonUi

fun getCountryInfoByCode(countryCode: String?): Pair<String, String> {
    return when (countryCode?.uppercase()) {
        "AD" -> Pair("🇦🇩", "Andorra")
        "AE" -> Pair("🇦🇪", "United Arab Emirates")
        "AF" -> Pair("🇦🇫", "Afghanistan")
        "AG" -> Pair("🇦🇬", "Antigua and Barbuda")
        "AI" -> Pair("🇦🇮", "Anguilla")
        "AL" -> Pair("🇦🇱", "Albania")
        "AM" -> Pair("🇦🇲", "Armenia")
        "AO" -> Pair("🇦🇴", "Angola")
        "AQ" -> Pair("🇦🇶", "Antarctica")
        "AR" -> Pair("🇦🇷", "Argentina")
        "AS" -> Pair("🇦🇸", "American Samoa")
        "AT" -> Pair("🇦🇹", "Austria")
        "AU" -> Pair("🇦🇺", "Australia")
        "AW" -> Pair("🇦🇼", "Aruba")
        "AX" -> Pair("🇦🇽", "Åland Islands")
        "AZ" -> Pair("🇦🇿", "Azerbaijan")
        "BA" -> Pair("🇧🇦", "Bosnia and Herzegovina")
        "BB" -> Pair("🇧🇧", "Barbados")
        "BD" -> Pair("🇧🇩", "Bangladesh")
        "BE" -> Pair("🇧🇪", "Belgium")
        "BF" -> Pair("🇧🇫", "Burkina Faso")
        "BG" -> Pair("🇧🇬", "Bulgaria")
        "BH" -> Pair("🇧🇭", "Bahrain")
        "BI" -> Pair("🇧🇮", "Burundi")
        "BJ" -> Pair("🇧🇯", "Benin")
        "BL" -> Pair("🇧🇱", "Saint Barthélemy")
        "BM" -> Pair("🇧🇲", "Bermuda")
        "BN" -> Pair("🇧🇳", "Brunei")
        "BO" -> Pair("🇧🇴", "Bolivia")
        "BQ" -> Pair("🇧🇶", "Caribbean Netherlands")
        "BR" -> Pair("🇧🇷", "Brazil")
        "BS" -> Pair("🇧🇸", "Bahamas")
        "BT" -> Pair("🇧🇹", "Bhutan")
        "BV" -> Pair("🇧🇻", "Bouvet Island")
        "BW" -> Pair("🇧🇼", "Botswana")
        "BY" -> Pair("🇧🇾", "Belarus")
        "BZ" -> Pair("🇧🇿", "Belize")
        "CA" -> Pair("🇨🇦", "Canada")
        "CC" -> Pair("🇨🇨", "Cocos (Keeling) Islands")
        "CD" -> Pair("🇨🇩", "Congo - Kinshasa")
        "CF" -> Pair("🇨🇫", "Central African Republic")
        "CG" -> Pair("🇨🇬", "Congo - Brazzaville")
        "CH" -> Pair("🇨🇭", "Switzerland")
        "CI" -> Pair("🇨🇮", "Côte d'Ivoire")
        "CK" -> Pair("🇨🇰", "Cook Islands")
        "CL" -> Pair("🇨🇱", "Chile")
        "CM" -> Pair("🇨🇲", "Cameroon")
        "CN" -> Pair("🇨🇳", "China")
        "CO" -> Pair("🇨🇴", "Colombia")
        "CR" -> Pair("🇨🇷", "Costa Rica")
        "CU" -> Pair("🇨🇺", "Cuba")
        "CV" -> Pair("🇨🇻", "Cape Verde")
        "CW" -> Pair("🇨🇼", "Curaçao")
        "CX" -> Pair("🇨🇽", "Christmas Island")
        "CY" -> Pair("🇨🇾", "Cyprus")
        "CZ" -> Pair("🇨🇿", "Czechia")
        "DE" -> Pair("🇩🇪", "Germany")
        "DJ" -> Pair("🇩🇯", "Djibouti")
        "DK" -> Pair("🇩🇰", "Denmark")
        "DM" -> Pair("🇩🇲", "Dominica")
        "DO" -> Pair("🇩🇴", "Dominican Republic")
        "DZ" -> Pair("🇩🇿", "Algeria")
        "EC" -> Pair("🇪🇨", "Ecuador")
        "EE" -> Pair("🇪🇪", "Estonia")
        "EG" -> Pair("🇪🇬", "Egypt")
        "EH" -> Pair("🇪🇭", "Western Sahara")
        "ER" -> Pair("🇪🇷", "Eritrea")
        "ES" -> Pair("🇪🇸", "Spain")
        "ET" -> Pair("🇪🇹", "Ethiopia")
        "FI" -> Pair("🇫🇮", "Finland")
        "FJ" -> Pair("🇫🇯", "Fiji")
        "FK" -> Pair("🇫🇰", "Falkland Islands")
        "FM" -> Pair("🇫🇲", "Micronesia")
        "FO" -> Pair("🇫🇴", "Faroe Islands")
        "FR" -> Pair("🇫🇷", "France")
        "GA" -> Pair("🇬🇦", "Gabon")
        "GB" -> Pair("🇬🇧", "United Kingdom")
        "GD" -> Pair("🇬🇩", "Grenada")
        "GE" -> Pair("🇬🇪", "Georgia")
        "GF" -> Pair("🇬🇫", "French Guiana")
        "GG" -> Pair("🇬🇬", "Guernsey")
        "GH" -> Pair("🇬🇭", "Ghana")
        "GI" -> Pair("🇬🇮", "Gibraltar")
        "GL" -> Pair("🇬🇱", "Greenland")
        "GM" -> Pair("🇬🇲", "Gambia")
        "GN" -> Pair("🇬🇳", "Guinea")
        "GP" -> Pair("🇬🇵", "Guadeloupe")
        "GQ" -> Pair("🇬🇶", "Equatorial Guinea")
        "GR" -> Pair("🇬🇷", "Greece")
        "GS" -> Pair("🇬🇸", "South Georgia & South Sandwich Islands")
        "GT" -> Pair("🇬🇹", "Guatemala")
        "GU" -> Pair("🇬🇺", "Guam")
        "GW" -> Pair("🇬🇼", "Guinea-Bissau")
        "GY" -> Pair("🇬🇾", "Guyana")
        "HK" -> Pair("🇭🇰", "Hong Kong SAR China")
        "HM" -> Pair("🇭🇲", "Heard & McDonald Islands")
        "HN" -> Pair("🇭🇳", "Honduras")
        "HR" -> Pair("🇭🇷", "Croatia")
        "HT" -> Pair("🇭🇹", "Haiti")
        "HU" -> Pair("🇭🇺", "Hungary")
        "ID" -> Pair("🇮🇩", "Indonesia")
        "IE" -> Pair("🇮🇪", "Ireland")
        "IL" -> Pair("🇮🇱", "Israel")
        "IM" -> Pair("🇮🇲", "Isle of Man")
        "IN" -> Pair("🇮🇳", "India")
        "IO" -> Pair("🇮🇴", "British Indian Ocean Territory")
        "IQ" -> Pair("🇮🇶", "Iraq")
        "IR" -> Pair("🇮🇷", "Iran")
        "IS" -> Pair("🇮🇸", "Iceland")
        "IT" -> Pair("🇮🇹", "Italy")
        "JE" -> Pair("🇯🇪", "Jersey")
        "JM" -> Pair("🇯🇲", "Jamaica")
        "JO" -> Pair("🇯🇴", "Jordan")
        "JP" -> Pair("🇯🇵", "Japan")
        "KE" -> Pair("🇰🇪", "Kenya")
        "KG" -> Pair("🇰🇬", "Kyrgyzstan")
        "KH" -> Pair("🇰🇭", "Cambodia")
        "KI" -> Pair("🇰🇮", "Kiribati")
        "KM" -> Pair("🇰🇲", "Comoros")
        "KN" -> Pair("🇰🇳", "St. Kitts & Nevis")
        "KP" -> Pair("🇰🇵", "North Korea")
        "KR" -> Pair("🇰🇷", "South Korea")
        "KW" -> Pair("🇰🇼", "Kuwait")
        "KY" -> Pair("🇰🇾", "Cayman Islands")
        "KZ" -> Pair("🇰🇿", "Kazakhstan")
        "LA" -> Pair("🇱🇦", "Laos")
        "LB" -> Pair("🇱🇧", "Lebanon")
        "LC" -> Pair("🇱🇨", "St. Lucia")
        "LI" -> Pair("🇱🇮", "Liechtenstein")
        "LK" -> Pair("🇱🇰", "Sri Lanka")
        "LR" -> Pair("🇱🇷", "Liberia")
        "LS" -> Pair("🇱🇸", "Lesotho")
        "LT" -> Pair("🇱🇹", "Lithuania")
        "LU" -> Pair("🇱🇺", "Luxembourg")
        "LV" -> Pair("🇱🇻", "Latvia")
        "LY" -> Pair("🇱🇾", "Libya")
        "MA" -> Pair("🇲🇦", "Morocco")
        "MC" -> Pair("🇲🇨", "Monaco")
        "MD" -> Pair("🇲🇩", "Moldova")
        "ME" -> Pair("🇲🇪", "Montenegro")
        "MF" -> Pair("🇲🇫", "St. Martin")
        "MG" -> Pair("🇲🇬", "Madagascar")
        "MH" -> Pair("🇲🇭", "Marshall Islands")
        "MK" -> Pair("🇲🇰", "North Macedonia")
        "ML" -> Pair("🇲🇱", "Mali")
        "MM" -> Pair("🇲🇲", "Myanmar (Burma)")
        "MN" -> Pair("🇲🇳", "Mongolia")
        "MO" -> Pair("🇲🇴", "Macao SAR China")
        "MP" -> Pair("🇲🇵", "Northern Mariana Islands")
        "MQ" -> Pair("🇲🇶", "Martinique")
        "MR" -> Pair("🇲🇷", "Mauritania")
        "MS" -> Pair("🇲🇸", "Montserrat")
        "MT" -> Pair("🇲🇹", "Malta")
        "MU" -> Pair("🇲🇺", "Mauritius")
        "MV" -> Pair("🇲🇻", "Maldives")
        "MW" -> Pair("🇲🇼", "Malawi")
        "MX" -> Pair("🇲🇽", "Mexico")
        "MY" -> Pair("🇲🇾", "Malaysia")
        "MZ" -> Pair("🇲🇿", "Mozambique")
        "NA" -> Pair("🇳🇦", "Namibia")
        "NC" -> Pair("🇳🇨", "New Caledonia")
        "NE" -> Pair("🇳🇪", "Niger")
        "NF" -> Pair("🇳🇫", "Norfolk Island")
        "NG" -> Pair("🇳🇬", "Nigeria")
        "NI" -> Pair("🇳🇮", "Nicaragua")
        "NL" -> Pair("🇳🇱", "Netherlands")
        "NO" -> Pair("🇳🇴", "Norway")
        "NP" -> Pair("🇳🇵", "Nepal")
        "NR" -> Pair("🇳🇷", "Nauru")
        "NU" -> Pair("🇳🇺", "Niue")
        "NZ" -> Pair("🇳🇿", "New Zealand")
        "OM" -> Pair("🇴🇲", "Oman")
        "PA" -> Pair("🇵🇦", "Panama")
        "PE" -> Pair("🇵🇪", "Peru")
        "PF" -> Pair("🇵🇫", "French Polynesia")
        "PG" -> Pair("🇵🇬", "Papua New Guinea")
        "PH" -> Pair("🇵🇭", "Philippines")
        "PK" -> Pair("🇵🇰", "Pakistan")
        "PL" -> Pair("🇵🇱", "Poland")
        "PM" -> Pair("🇵🇲", "St. Pierre & Miquelon")
        "PN" -> Pair("🇵🇳", "Pitcairn Islands")
        "PR" -> Pair("🇵🇷", "Puerto Rico")
        "PT" -> Pair("🇵🇹", "Portugal")
        "PW" -> Pair("🇵🇼", "Palau")
        "PY" -> Pair("🇵🇾", "Paraguay")
        "QA" -> Pair("🇶🇦", "Qatar")
        "RE" -> Pair("🇷🇪", "Réunion")
        "RO" -> Pair("🇷🇴", "Romania")
        "RS" -> Pair("🇷🇸", "Serbia")
        "RU" -> Pair("🇷🇺", "Russia")
        "RW" -> Pair("🇷🇼", "Rwanda")
        "SA" -> Pair("🇸🇦", "Saudi Arabia")
        "SB" -> Pair("🇸🇧", "Solomon Islands")
        "SC" -> Pair("🇸🇨", "Seychelles")
        "SD" -> Pair("🇸🇩", "Sudan")
        "SE" -> Pair("🇸🇪", "Sweden")
        "SG" -> Pair("🇸🇬", "Singapore")
        "SH" -> Pair("🇸🇭", "St. Helena")
        "SI" -> Pair("🇸🇮", "Slovenia")
        "SJ" -> Pair("🇸🇯", "Svalbard & Jan Mayen")
        "SK" -> Pair("🇸🇰", "Slovakia")
        "SL" -> Pair("🇸🇱", "Sierra Leone")
        "SM" -> Pair("🇸🇲", "San Marino")
        "SN" -> Pair("🇸🇳", "Senegal")
        "SO" -> Pair("🇸🇴", "Somalia")
        "SR" -> Pair("🇸🇷", "Suriname")
        "SS" -> Pair("🇸🇸", "South Sudan")
        "ST" -> Pair("🇸🇹", "Sao Tome & Principe")
        "SV" -> Pair("🇸🇻", "El Salvador")
        "SX" -> Pair("🇸🇽", "Sint Maarten")
        "SY" -> Pair("🇸🇾", "Syria")
        "SZ" -> Pair("🇸🇿", "Eswatini")
        "TC" -> Pair("🇹🇨", "Turks & Caicos Islands")
        "TD" -> Pair("🇹🇩", "Chad")
        "TF" -> Pair("🇹🇫", "French Southern Territories")
        "TG" -> Pair("🇹🇬", "Togo")
        "TH" -> Pair("🇹🇭", "Thailand")
        "TJ" -> Pair("🇹🇯", "Tajikistan")
        "TK" -> Pair("🇹🇰", "Tokelau")
        "TL" -> Pair("🇹🇱", "Timor-Leste")
        "TM" -> Pair("🇹🇲", "Turkmenistan")
        "TN" -> Pair("🇹🇳", "Tunisia")
        "TO" -> Pair("🇹🇴", "Tonga")
        "TR" -> Pair("🇹🇷", "Turkey")
        "TT" -> Pair("🇹🇹", "Trinidad & Tobago")
        "TV" -> Pair("🇹🇻", "Tuvalu")
        "TZ" -> Pair("🇹🇿", "Tanzania")
        "UA" -> Pair("🇺🇦", "Ukraine")
        "UG" -> Pair("🇺🇬", "Uganda")
        "UM" -> Pair("🇺🇲", "U.S. Outlying Islands")
        "US" -> Pair("🇺🇸", "United States")
        "UY" -> Pair("🇺🇾", "Uruguay")
        "UZ" -> Pair("🇺🇿", "Uzbekistan")
        "VA" -> Pair("🇻🇦", "Vatican City")
        "VC" -> Pair("🇻🇨", "St. Vincent & Grenadines")
        "VE" -> Pair("🇻🇪", "Venezuela")
        "VG" -> Pair("🇻🇬", "British Virgin Islands")
        "VI" -> Pair("🇻🇮", "U.S. Virgin Islands")
        "VN" -> Pair("🇻🇳", "Vietnam")
        "VU" -> Pair("🇻🇺", "Vanuatu")
        "WF" -> Pair("🇼🇫", "Wallis & Futuna")
        "WS" -> Pair("🇼🇸", "Samoa")
        "YE" -> Pair("🇾🇪", "Yemen")
        "YT" -> Pair("🇾🇹", "Mayotte")
        "ZA" -> Pair("🇿🇦", "South Africa")
        "ZM" -> Pair("🇿🇲", "Zambia")
        "ZW" -> Pair("🇿🇼", "Zimbabwe")
        else -> Pair("", "")
    }
}