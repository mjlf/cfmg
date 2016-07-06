package com.mjlf.cfmg.entity;

import java.util.Date;

public class LibraryFile {
	protected long id;
	protected long libraryId;
	protected String fileName;
	protected String filePath;
	protected long fileLength;
	protected String fileType;
	protected long libraryBookId;
	protected long uploadUserId;
	protected Date uploadTime;
	protected String uploadUserName;
	protected String uploadTimeStr;
	protected String fileNowName;
	protected String downloadPath;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(long libraryId) {
		this.libraryId = libraryId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getLibraryBookId() {
		return libraryBookId;
	}
	public void setLibraryBookId(long libraryBookId) {
		this.libraryBookId = libraryBookId;
	}
	public String getUploadUserName() {
		return uploadUserName;
	}
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}
	public String getUploadTimeStr() {
		return uploadTimeStr;
	}
	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}
	public String getFileNowName() {
		return fileNowName;
	}
	public void setFileNowName(String fileNowName) {
		this.fileNowName = fileNowName;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	@Override
	public String toString() {
		return "LibraryFile [id=" + id + ", libraryId=" + libraryId + ", fileName=" + fileName + ", filePath="
				+ filePath + ", fileLength=" + fileLength + ", fileType=" + fileType + ", libraryBookId="
				+ libraryBookId + ", uploadUserId=" + uploadUserId + ", uploadTime=" + uploadTime + "]";
	}
}
