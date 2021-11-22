
def call(int level, String message, String formatType) {
    String border = createSymbolString(message.length()+4, formatType)
    String logLine = LogLevel(level, message)
    println border
    println " ${logLine} "
    println border
}

String createSymbolString(int logLength, String formatType) {
    if (logLength <= 0) {
        return ""
    } else {
        switch (formatType) {
            case 'equal':
                return '=' + createSymbolString(--logLength, formatType)
                break
            case 'underline':
                return '_' + createSymbolString(--logLength, formatType)
                break
            case 'dash':
                return '-' + createSymbolString(--logLength, formatType)
                break
            case 'star':
                return '*' + createSymbolString(--logLength, formatType)
                break
            default:
                return ""
                break
        }
    }
}

String LogLevel(int level, String message) {
    switch (level) {
        case 1:
            return "Error :: " + message
            break
        case 2:
            return "Warning :: " + message
            break
        case 3:
            return "Notice :: " + message
            break
        case 4:
            return "Info :: " + message
            break
        case 5:
            return "Debug :: " + message
            break
        default:
            return message
            break
    }
}

