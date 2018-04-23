package com.arcao.geocaching.api;

import org.jetbrains.annotations.NotNull;

public enum StatusCode {
    // TODO
    OK(0),
    Fail(1),
    NotAuthorized(2),
    AccessTokenExpired(3),
    UserDidNotAuthorize(4),
    UserTokenNotValid(5),
    ApplicationTokenNotValid(6),
    ApplicationNotApproved(7),
    CacheNotAvailable(8),
    NotImplemented(9),
    WptLogTypeIdInvalid(10),
    CannotLogToLockedCache(11),
    GeocacheCodeIsNotValid(12),
    GeocacheNotFound(13),
    AccountNotFound(14),
    InvalidDateTime(15),
    InvalidNote(16),
    NoteTooLong(17),
    SessionExpired(18),
    PhotoCaptionTooLong(19),
    PhotoDescriptionTooLong(20),
    NoImageData(21),
    ProfileLoadFailed(22),
    LogLoadFailed(23),
    LogNotOwned(24),
    ImageUploadFailed(25),
    CacheNoteUpdateFailed(26),
    CacheNoteDeleteFailed(27),
    TrackableLogFailed(28),
    LogTypeNotProvided(29),
    ValidateSearchDataFailed(30),
    ValidateNotFoundByUserNoBasicMembershipFail(31),
    ValidateNotFoundByUserBasicMembershipFail(32),
    ValidateNotFoundByUserPremiumMembershipFail(33),
    SouvenirNoPublicGuidFail(34),
    TrackableLogTypeInvalid(35),
    TravelBugCodeInvalid(36),
    TrackingCodeRequired(37),
    TrackingCodeInvalid(38),
    TrackableIsArchived(39),
    TrackableMustBeHeldByUser(40),
    TrackableAlreadyHeld(41),
    TrackableAlreadyMarkedAsMissing(42),
    TrackableUnableToBeMarkedMissing(43),
    TrackableMustBeInCache(44),
    TrackableRequiredCacheCode(45),
    NotEnoughFavoritePoints(46),
    CacheAlreadyRated(47),
    CacheNotLoggedYet(48),
    FailedToFavoriteCache(49),
    FailedToUnfavoriteCache(50),
    CacheNotFavoritedYet(51),
    UnableToRetrieveUsersAvailableFavoritePoints(52),
    UnableToRetrieveUsersWhoFavoritedCache(53),
    FailedToRetrieveFavoritedCache(54),
    StoredSearchNotFound(55),

    // NotHiddenByUser filter
    WrongUserCountForNotHiddenByUserFilter(56),
    AtLeastBasicMembershipRequiredForNotHiddenByUserFilter(57),
    PremiumMembershipRequiredForNotHiddenByUserFilter(58),

    // HiddenByUser filter
    WrongUserCountForHiddenByUserFilter(59),
    AtLeastBasicMembershipRequiredForHiddenByUserFilter(60),
    PremiumMembershipRequiredForHiddenByUserFilter(61),

    // BookmarksExclude filter
    InvalidBookmarkListId(62),
    AccountNotValidForRetrieveBookmarks(63),
    PremiumMembershipRequiredForBookmarksExcludeFilter(64),

    // TrackableCount filter
    InvalidValueForMinMaxTrackables(65),
    PremiumMembershipRequiredForTrackableCountFilter(66),

    // Terrain filter
    InvalidValueForMinMaxTerrain(76),
    PremiumMembershipRequiredForTerrainFilter(77),

    // Terrain filter
    InvalidValueForMinMaxDifficulty(78),
    PremiumMembershipRequiredForDifficultyFilter(79),

    // FavoritePoints filter
    MissingValueForMinMaxFavoritePoints(80),
    MinFavoritePointsCannotBeGreaterThanMaxFavoritePoints(81),
    NonNegativeValueRequiredForMinMaxFavoritePoints(82),
    NonNegativeValueRequiredForMinFavoritePoints(83),
    NonNegativeValueRequiredForMaxFavoritePoints(84),
    PremiumMembershipRequiredForFavoritePointsFilter(85),

    // GeocacheName filter
    MissingValueForGeocacheName(86),
    PremiumMembershipRequiredForGeocacheNameFilter(87),

    FilterNotImplemented(88),
    CacheCodeFilterOrPointRadiusFilterRequired(89),
    MethodNotSupplied(90),

    // GeocacheType filter
    InvalidValueForGeocacheTypeIds(91),

    // GeocacheContainerSize filter
    InvalidValueForGeocacheContainerSizeIds(92),
    PremiumMembershipRequiredForGeocacheContainerSizeFilter(93),

    UnableToRetrieveUserProfile(99),
    PublicProfileNotFound(103),

    MaximumRequestsPerPageExceeded(113),
    CacheLimitExceeded(118),
    NoNoteOnThisCacheToDelete(125),
    CacheLimitExceededInRequest(132),
    UserNotFound(139),
    NumberOfCallsExceeded(140),
    AccessTokenNotValid(141),
    Other(-1);
    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @NotNull
    public static StatusCode getByCode(int code) {
        for (StatusCode statusCode : values()) {
            if (statusCode.code == code) {
                return statusCode;
            }
        }

        return Fail;
    }


}
