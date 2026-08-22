package test

import munit.FunSuite
import service.ArtistService
import mock.MockArtistRepository

class ArtistServiceSuite extends FunSuite:

    val mock = MockArtistRepository()
    val service = ArtistService(mock)

    test("findall returns all artists"):
        assertEquals(service.findAll().length, 2)
    
    test("create add artists"):
        val result = service.create("Nirvana")
        assert(result.isRight)
        assertEquals(service.findAll().length, 3)

    test("create rejects empty name"):
        val result = service.create("")
        assert(result.isLeft)

    test("delete removes artist"):
        assert(service.delete(1))
        assertEquals(service.findAll().length, 1)

    test("delete returns false non-existence"):
        assert(!service.delete(999))
