
def call (String data) {
    byte[] dataDecode = data.decodeBase64()
    return new String(dataDecode)
}

