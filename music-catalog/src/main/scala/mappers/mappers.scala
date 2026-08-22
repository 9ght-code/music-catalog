package mappers

import model.Artist

extension (a: Artist)
    def toObj = ujson.Obj("id" -> a.id, "name" -> a.name)