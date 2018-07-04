package com.iceze.push_notification_service.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_NOTE;
import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_FILE;
import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_LINK;

public enum PushType {
	@SerializedName("note")
	NOTE(PUSH_TYPE_NAME_NOTE),
	@SerializedName("file")
	FILE(PUSH_TYPE_NAME_FILE),
	@SerializedName("link")
	LINK(PUSH_TYPE_NAME_LINK);
	
	private String displayName;
	
	PushType(final String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() {
        return this.displayName;
	}
	
	/**
     * Find Push Type by the given display name.
     * @param displayName, String representing display name.
     * 
     * @return PushType
     */
    public static PushType findByDisplayName(final String displayName) {
    	List<PushType> list = Arrays.asList(PushType.values()).stream().filter(t -> t.displayName.equals(displayName))
    																		   .collect(Collectors.toList());
    	return (list.isEmpty()) ? null : list.get(0);
    }
}
