package com.arcao.geocaching.api.data.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enumeration for all existing Cache types.
 *
 * @author arcao
 */
public enum GeocacheType {
    /**
     * This is the original cache type consisting, at a bare minimum, a container and a log book. Normally you'll find a tupperware container, ammo box, or bucket filled with goodies, or smaller container ("micro cache") too small to contain items except for a log book. The coordinates listed on the traditional cache page is the exact location for the cache.
     */
    Traditional("Traditional Cache", 2),
    /**
     * A multi-cache ("multiple") involves two or more locations, the final location being a physical container. There are many variations, but most multi-caches have a hint to find the second cache, and the second cache has hints to the third, and so on. An offset cache (where you go to a location and get hints to the actual cache) is considered a multi-cache.
     */
    Multi("Multi-cache", 3),
    /**
     * A Mystery Cache is the “catch-all” of cache types, this form of cache can involve complicated puzzles you will first need to solve to determine the coordinates. The only commonality of this cache type is that the coordinates listed are not of the actual cache location but a general reference point, such as a nearby parking location. Due to the increasing creativity of geocaching this becomes the staging ground for new and unique challenges.
     */
    Mystery("Mystery Cache", 8),
    /**
     * A virtual cache is a cache that exists in a form of a location. Depending on the cache "hider," a virtual cache could be to answer a question about a location, an interesting spot, a task, etc. The reward for these caches is the location itself and sharing information about your visit.  <br><br>  Because of the nature of these geocaches, you must actually visit the location and acquire the coordinates there before you can post. In addition, although many locations are interesting, a virtual cache should be out of the ordinary enough to warrant logging a visit.  <br><br>  Virtuals are now considered <a href="http://www.waymarking.com">waymarks on Waymarking.com</a>.
     */
    Virtual("Virtual Cache", 4),
    /**
     * An Earthcache is an educational form of a <i>virtual cache</i>.  The reward for these caches is learning more about the planet on which we live - its landscapes, its geology or the minerals and fossils that are found there.  Many Earthcaches are in National Parks.  Some are multi-cache in form, and some have a physical log book located in or close to a Visitor Center. Earthcaches are developed in association with the Geological Society of America.  For more information go to <a href="http://www.geosociety.org/earthcache/">http://www.geosociety.org/earthcache/</a>
     */
    Earth("Earthcache", 137),
    /**
     * A Project APE cache is a geocache hidden as part of a Planet of the Apes FOX Promotion in 2001. When each cache was placed they contained a different prop from the movie. There are still some active today.
     */
    ProjectApe("Project APE Cache", 9),
    /**
     * A letterbox is another form of treasure hunting using clues instead of coordinates. In some cases, however, a letterbox has coordinates, and the owner has made it a letterbox and a geocache. To read more about letterboxing, visit the <a href="http://www.letterboxing.org">Letterboxing North America</a> web site.
     */
    LetterboxHybrid("Letterbox Hybrid", 5),
    /**
     * A Wherigo geocache uses your Wherigo cartridge to lead you ultimately to the physical geocache location. The cartridges must reside at Wherigo.com. If a cartridge is used as a requirement to find a geocache, it is considered a Wherigo cache, regardless of whether it may also have a puzzle or multi-cache component.
     */
    Wherigo("Wherigo Cache", 1858),
    /**
     * An Event Cache is when local geocachers and geocaching organizations designate a time and location to meet and discuss geocaching. After the event happens the cache listing is archived.
     */
    Event("Event Cache", 6),
    /**
     * A Mega-Event cache is similar to an Event Cache but it is much larger.  In order to qualify as a Mega Event, the event cache must be attended by 500+ people.  Typically, Mega Events are usually annual events and can attract geocachers from all over the world.
     */
    MegaEvent("Mega-Event Cache", 453),
    /**
     * Cache In Trash Out Events are organized clean-up events that involve and benefit the larger community. <a href="http://www.cacheintrashout.org" target="_blank">Cache In Trash Out</a> is an activity intimately tied to geocaching. While out there on a cache hunt, geocachers collect litter along the trails and properly dispose of it.
     */
    CacheInTrashOutEvent("Cache In Trash Out Event", 13),
    /**
     * The GPS Adventures Maze is a traveling educational exhibit developed by Groundspeak, Trimble and Minotaur Maze Exhibits to teach people of all ages about navigation, GPS technology and geocaching.
     */
    GpsAdventuresExhibit("GPS Adventures Exhibit", 1304),
    /**
     * A Webcam "cache" are caches that use existing web cameras placed by individuals or agencies that monitor various areas like parks or road conditions. The idea is to get yourself in front of the camera to log your visit. The challenging part, however, it that you need to call a friend to look up the web site that displays the camera shot. You will need to have them to save the picture to log the cache. You could also use your wireless modem or cell phone and save the image yourself on your laptop. Webcam caches are now <a href="http://www.waymarking.com/waymarks/default.aspx?f=-1&amp;DCTGUID=d23f8e0b-2e43-4530-8f46-2e4fdae93ddd">in the Web Camera category on Waymarking.com</a>
     */
    Webcam("Webcam Cache", 11),
    /**
     * Locationless Caches could be considered the opposite of a Traditional Cache. Instead of finding a hidden container, you are given a task to locate a specific object and log its coordinates. A scavenger hunt of sorts, it involves collecting waypoints of various objects around the world.  <br><br>  Locationless caches have evolved into <a href="http://www.waymarking.com">Waymarking</a>. Waymark categories are similar to how location-less caches were listed on geocaching.com, but you can now search for the locations in each category.
     */
    Locationless("Locationless (Reverse) Cache", 12),
    /**
     * A 10 Years! Event Cache is a special Event Cache type for events held April 30 - May 3, 2010 to celebrate 10 years of geocaching.
     */
    LostAndFoundEvent("Lost and Found Event Caches", 3653),
    /**
     * Groundspeak HQ Cache.
     */
    GroudspeakHQ("Groundspeak HQ", 3773),
    /**
     * Groundspeak Lost and Found Celebration.
     */
    GroudspeakLostAndFoundCelebration("Groundspeak Lost and Found Celebration", 3774),
    /**
     * Groundspeak Block Party.
     */
    GroundspeakBlockParty("Groundspeak Block Party", 4738),
    /**
     * A Giga-Event cache is similar to an Event Cache but it is much larger.  In order to qualify as a Giga Event, the event cache must be attended by 5000+ people.  Typically, Giga Events are usually annual events and can attract geocachers from all over the world.
     */
    GigaEvent("Giga-Event Cache", 7005);

    /**
     * Friendly name.
     */
    @NotNull public final String name;
    /**
     * Groundspeak Id.
     */
    public final int id;

    GeocacheType(@NotNull String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get a cache type from friendly name. Name is case sensitive. If name doesn't correspond with any GeocacheType enum item, the null is returned.
     *
     * @param name friendly name
     * @return cache type or null
     */
    @Nullable
    public static GeocacheType fromName(@Nullable String name) {
        for (GeocacheType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        return null;
    }

    /**
     * Get a cache type from Groundspeak Id. If Groundspeak Id doesn't correspond with any GeocacheType enum item, the null is returned.
     *
     * @param id Groundspeak Id
     * @return cache type or null
     */
    @Nullable
    public static GeocacheType fromId(int id) {
        for (GeocacheType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }

}
