package com.arcao.geocaching.api.impl.live_geocaching_api;

public enum StatusCode {
  // TODO
  OK(0),
  Fail(1),
  NotAuthorized(2),
  UserAccountProblem(3),
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
  NotEnoughFavoritePPoints(46),
  CacheAlreadyRated(47),
  CacheNotLoggedYet(48),
  FailedToFavoriteCache(49),
  FailedToUnfavoriteCache(50),
  CacheNotFavoritedYet(51),
  StoredSearchNotFound(55),
  InvalidBookmarkListId(62),
  NoClampingFilterProvided(89),
  MethodNotSupplied(90),
  CacheLimitExceeded(118),
  CacheLimitExceededInRequest(132),
  UserNotFound(139),
  NumberOfCallsExceded(140),
  AccessTokenNotValid(141),
  Other(-1);
	private int code;
	
	private StatusCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static StatusCode parseStatusCode(int code) {
		for (StatusCode statusCode : values()) {
			if (statusCode.getCode() == code)
				return statusCode;
		}
		
		return Fail;
	}
}
