package com.arcao.geocaching.api.data.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum GeocacheLogType {
    Unknown("Unknown", 0),
    FoundIt("Found it", 2),
    DidntFindIt("Didn't find it", 3),
    WriteNote("Write note", 4),
    NeedsMaintenance("Needs Maintenance", 45),
    OwnerMaintenance("Owner Maintenance", 46),
    PublishListing("Publish Listing", 24),
    EnableListing("Enable Listing", 23),
    TemporarilyDisableListing("Temporarily Disable Listing", 22),
    UpdateCoordinates("Update Coordinates", 47),
    Announcement("Announcement", 74),
    WillAttend("Will Attend", 9),
    Attended("Attended", 10),
    PostReviewerNote("Post Reviewer Note", 68),
    NeedsArchived("Needs Archived", 7),
    WebcamPhotoTaken("Webcam Photo Taken", 11),
    RetractListing("Retract Listing", 25),
    Archive("Archive", 5),
    Unarchive("Unarchive", 1);

    /**
     * Friendly name.
     */
    @NotNull public final String name;
    /**
     * Groundspeak Id.
     */
    public final int id;

    GeocacheLogType(@NotNull String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get a GeocacheLogType from Friendly name. If Friendly name is not valid, the null is returned.
     *
     * @param name Friendly name
     * @return Geocache Log Type or null
     */
    @Nullable
    public static GeocacheLogType fromName(@Nullable String name) {
        for (final GeocacheLogType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        return null;
    }
}
