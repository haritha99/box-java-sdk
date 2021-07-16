package com.box.sdk;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Represents a sign request. Sign requests are used to prepare documents for signing and send them to signers.
 *
 * @see <a href="https://docs.box.com/reference#sign-request-object">Box sign request</a>
 *
 * <p>Unless otherwise noted, the methods in this class can throw an unchecked {@link BoxAPIException} (unchecked
 * meaning that the compiler won't force you to handle it) if an error occurs. If you wish to implement custom error
 * handling for errors related to the Box REST API, you should capture this exception explicitly.</p>
 */
@BoxResourceType("sign_request")
public class BoxSignRequest extends BoxResource {

    /**
     * The URL template used for operation with sign requests.
     */
    public static final URLTemplate SIGN_REQUESTS_URL_TEMPLATE = new URLTemplate("sign_requests");

    /**
     * The URL template used for operation with sign request with given ID.
     */
    public static final URLTemplate SIGN_REQUEST_URL_TEMPLATE = new URLTemplate("sign_requests/%s");

    /**
     * The URL template used to cancel existing sign request.
     */
    public static final URLTemplate SIGN_REQUEST_CANCEL_URL_TEMPLATE = new URLTemplate("sign_requests/%s/cancel");

    /**
     * The URL template used to resend existing sign request.
     */
    public static final URLTemplate SIGN_REQUEST_RESEND_URL_TEMPLATE = new URLTemplate("sign_requests/%s/resend");

    /**
     * The default limit of entries per response.
     */
    private static final int DEFAULT_LIMIT = 100;

    /**
     * Constructs a BoxResource for a resource with a given ID.
     *
     * @param api the API connection to be used by the resource.
     * @param id  the ID of the resource.
     */
    public BoxSignRequest(BoxAPIConnection api, String id) {
        super(api, id);
    }

    /**
     * Used to create a new sign request.
     *
     * @param api            the API connection to be used by the created user.
     * @param signers        the list of signers for this sign request.
     * @param sourceFiles    the list of files to a signing document from.
     * @param parentFolderId the id of destination folder to place sign request specific data
     * @return the created sign request's info.               .
     */
    public static BoxSignRequest.Info createSignRequest(BoxAPIConnection api, List<BoxSignRequestSigner> signers,
                                                        List<BoxSignRequestFile> sourceFiles, String parentFolderId) {
        return createSignRequest(api, signers, sourceFiles, parentFolderId, null);
    }

    /**
     * Used to create a new sign request with optional parameters.
     *
     * @param api            the API connection to be used by the created user.
     * @param signers        the list of signers for this sign request.
     * @param sourceFiles    the list of files to a signing document from.
     * @param parentFolderId the id of destination folder to place sign request specific data
     * @param optionalParams the optional parameters.
     * @return the created sign request's info.               .
     */
    public static BoxSignRequest.Info createSignRequest(BoxAPIConnection api, List<BoxSignRequestSigner> signers,
                                                        List<BoxSignRequestFile> sourceFiles, String parentFolderId,
                                                        BoxSignRequestCreateParams optionalParams) {

        JsonObject requestJSON = new JsonObject();

        JsonArray signersJSON = new JsonArray();
        for (BoxSignRequestSigner signer : signers) {
            signersJSON.add(signer.getJSONObject());
        }
        requestJSON.add("signers", signersJSON);

        JsonArray sourceFilesJSON = new JsonArray();
        for (BoxSignRequestFile sourceFile : sourceFiles) {
            sourceFilesJSON.add(sourceFile.getJSONObject());
        }
        requestJSON.add("source_files", sourceFilesJSON);

        JsonObject parentFolderJSON = new JsonObject();
        parentFolderJSON.add("id", parentFolderId);
        parentFolderJSON.add("type", "folder");
        requestJSON.add("parent_folder", parentFolderJSON);

        if (optionalParams != null) {
            optionalParams.appendParamsAsJson(requestJSON);
        }

        URL url = SIGN_REQUESTS_URL_TEMPLATE.build(api.getBaseURL());
        BoxJSONRequest request = new BoxJSONRequest(api, url, "POST");
        request.setBody(requestJSON.toString());
        BoxJSONResponse response = (BoxJSONResponse) request.send();
        JsonObject responseJSON = JsonObject.readFrom(response.getJSON());
        BoxSignRequest signRequest = new BoxSignRequest(api, responseJSON.get("id").asString());
        return signRequest.new Info(responseJSON);
    }

    /**
     * Returns information about this sign request.
     *
     * @param fields the fields to retrieve.
     * @return information about this sign request.
     */
    public BoxSignRequest.Info getInfo(String... fields) {
        QueryStringBuilder builder = new QueryStringBuilder();
        if (fields.length > 0) {
            builder.appendParam("fields", fields);
        }
        URL url = SIGN_REQUEST_URL_TEMPLATE.buildAlphaWithQuery(
                this.getAPI().getBaseURL(), builder.toString(), this.getID());
        BoxAPIRequest request = new BoxAPIRequest(this.getAPI(), url, "GET");
        BoxJSONResponse response = (BoxJSONResponse) request.send();
        JsonObject responseJSON = JsonObject.readFrom(response.getJSON());
        return new BoxSignRequest.Info(responseJSON);
    }

    /**
     * Returns all the sign requests.
     *
     * @param api    the API connection to be used by the resource.
     * @param fields the fields to retrieve.
     * @return an iterable with all the sign requests.
     */
    public static Iterable<BoxSignRequest.Info> getAll(final BoxAPIConnection api, String... fields) {
        return getAll(DEFAULT_LIMIT, api, fields);
    }

    /**
     * Returns all the sign requests.
     *
     * @param limit  the limit of items per single response. The default value is 100.
     * @param api    the API connection to be used by the resource.
     * @param fields the fields to retrieve.
     * @return an iterable with all the sign requests.
     */
    public static Iterable<BoxSignRequest.Info> getAll(int limit, final BoxAPIConnection api, String... fields) {
        QueryStringBuilder queryString = new QueryStringBuilder();
        if (fields.length > 0) {
            queryString.appendParam("fields", fields);
        }
        URL url = SIGN_REQUESTS_URL_TEMPLATE.buildWithQuery(api.getBaseURL(), queryString.toString());
        return new BoxResourceIterable<BoxSignRequest.Info>(api, url, limit) {

            @Override
            protected BoxSignRequest.Info factory(JsonObject jsonObject) {
                BoxSignRequest signRequest = new BoxSignRequest(api, jsonObject.get("id").asString());
                return signRequest.new Info(jsonObject);
            }

        };
    }

    /**
     * Cancels a sign request.
     *
     * @return the created sign request's info.
     */
    public BoxSignRequest.Info cancel() {
        URL url = SIGN_REQUEST_CANCEL_URL_TEMPLATE.buildAlphaWithQuery(getAPI().getBaseURL(), "", this.getID());
        BoxJSONRequest request = new BoxJSONRequest(getAPI(), url, "POST");
        BoxJSONResponse response = (BoxJSONResponse) request.send();
        JsonObject responseJSON = JsonObject.readFrom(response.getJSON());
        return new BoxSignRequest.Info(responseJSON);
    }

    /**
     * Resends a sign request to all signers that have not signed yet. There is a 10 minute cooling-off period between
     * sending a mail.
     *
     * @return true if request was successful, otherwise false.
     */
    public boolean resend() {
        URL url = SIGN_REQUEST_RESEND_URL_TEMPLATE.buildAlphaWithQuery(getAPI().getBaseURL(), "", this.getID());
        BoxJSONRequest request = new BoxJSONRequest(getAPI(), url, "POST");
        BoxAPIResponse response = request.send();
        return response.getResponseCode() == 202;
    }

    /**
     * Contains information about the sign request.
     */
    public class Info extends BoxResource.Info {

        private boolean isDocumentPreparationNeeded;
        private String redirectUrl;
        private String declinedRedirectUrl;
        private List<BoxSignRequestRequiredAttachment> requiredAttachments;
        private boolean areAttachmentsEnabled;
        private boolean areTextSignaturesEnabled;
        private boolean isTextEnabled;
        private boolean areDatesEnabled;
        private boolean areEmailsDisabled;
        private BoxSignRequestSignatureColor signatureColor;
        private boolean isPhoneVerificationRequiredToView;
        private String emailSubject;
        private String emailMessage;
        private boolean areRemindersEnabled;
        private List<BoxFile.Info> sourceFiles;
        private BoxFolder.Info parentFolder;
        private List<BoxSignRequestSigner> signers;
        private String name;
        private List<BoxSignRequestPrefillTag> prefillTags;
        private Integer daysValid;
        private String externalId;
        private String prepareUrl;
        private BoxFile.Info signingLog;
        private BoxSignRequestStatus status;
        private BoxSignRequestSignFiles signFiles;
        private Date autoExpireAt;
        private Date createdAt;
        private Date updatedAt;

        /**
         * Constructs an empty Info object.
         */
        public Info() {
            super();
        }

        /**
         * Constructs an Info object by parsing information from a JSON string.
         *
         * @param json the JSON string to parse.
         */
        public Info(String json) {
            super(json);
        }

        /**
         * Constructs an Info object using an already parsed JSON object.
         *
         * @param jsonObject the parsed JSON object.
         */
        Info(JsonObject jsonObject) {
            super(jsonObject);
        }

        /**
         * Gets the flag indicating if the sender should be taken into the builder flow to prepare the document.
         *
         * @return true if document preparation is needed, otherwise false.
         */
        public boolean getIsDocumentPreparationNeeded() {
            return this.isDocumentPreparationNeeded;
        }

        /**
         * Gets the uri that a signer will be redirect to after signing a document.
         * If no declined redirect url is specified, this will be used for decline actions as well.
         *
         * @return redirect url.
         */
        public String getRedirectUrl() {
            return this.redirectUrl;
        }

        /**
         * Gets the uri that a signer will be redirect to after declining to sign a document.
         *
         * @return declined redirect url.
         */
        public String getDeclinedRedirectUrl() {
            return this.declinedRedirectUrl;
        }

        /**
         * Gets the attachments that signers are required to upload.
         *
         * @return list of attachments.
         */
        public List<BoxSignRequestRequiredAttachment> getRequiredAttachments() {
            return this.requiredAttachments;
        }

        /**
         * Gets the flag indicating if uploading/adding attachments for signers is enabled.
         *
         * @return true if attachments uploading/adding is enabled for signers, otherwise false.
         */
        public boolean getAreAttachmentsEnabled() {
            return this.areAttachmentsEnabled;
        }

        /**
         * Gets the flag indicating if usage of signatures generated by typing (text) is enabled.
         *
         * @return true if text signatures are enabled, otherwise false.
         */
        public boolean getAreTextSignaturesEnabled() {
            return this.areTextSignaturesEnabled;
        }

        /**
         * Gets the flag indicating if ability for signer to add text is enabled.
         *
         * @return true if ability for signer to add text is enabled, otherwise false.
         */
        public boolean getIsTextEnabled() {
            return this.isTextEnabled;
        }

        /**
         * Gets the flag indicating if ability for signer to add dates is enabled.
         *
         * @return true if ability for signer to add dates is enabled, otherwise false.
         */
        public boolean getAreDatesEnabled() {
            return this.areDatesEnabled;
        }

        /**
         * Gets the flag indicating if all status emails,
         * as well as the original email that contains the sign request are disabled.
         *
         * @return true if emails are disabled, otherwise false.
         */
        public boolean getAreEmailsDisabled() {
            return this.areEmailsDisabled;
        }

        /**
         * Gets the forced, specific color for the signature.
         *
         * @return signature color (blue, black, red).
         */
        public BoxSignRequestSignatureColor getSignatureColor() {
            return this.signatureColor;
        }

        /**
         * Gets the flag indicating if signers are forced to verify a text message prior to viewing the document.
         *
         * @return true if phone verification is required to view document, otherwise false.
         */
        public boolean getIsPhoneVerificationRequiredToView() {
            return this.isPhoneVerificationRequiredToView;
        }

        /**
         * Gets the subject of sign request email.
         *
         * @return subject of sign request email.
         */
        public String getEmailSubject() {
            return this.emailSubject;
        }

        /**
         * Gets the message to include in sign request email.
         *
         * @return message of sign request email.
         */
        public String getEmailMessage() {
            return this.emailMessage;
        }

        /**
         * Gets the flag indicating if remind for signers to sign a document on day 3, 8, 13 and 18
         * (or less if the document has been digitally signed already) is enabled.
         *
         * @return true if reminders are enabled, otherwise false.
         */
        public boolean getAreRemindersEnabled() {
            return this.areRemindersEnabled;
        }

        /**
         * Gets the list of files to create a signing document from.
         *
         * @return list of files to create a signing document from.
         */
        public List<BoxFile.Info> getSourceFiles() {
            return this.sourceFiles;
        }

        /**
         * Gets the destination folder to place sign request specific data in (copy of source files, signing log etc.).
         *
         * @return destination folder to place sign request specific data in.
         */
        public BoxFolder.Info getParentFolder() {
            return this.parentFolder;
        }

        /**
         * Gets the list of signers for this sign request.
         *
         * @return list of signers for this sign request.
         */
        public List<BoxSignRequestSigner> getSigners() {
            return this.signers;
        }

        /**
         * Gets the name of this sign request.
         *
         * @return name of this sign request.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets the list of prefill tags.
         *
         * @return list of prefill tags.
         */
        public List<BoxSignRequestPrefillTag> getPrefillTags() {
            return this.prefillTags;
        }

        /**
         * Gets the number of days after which this request will automatically expire if not completed.
         *
         * @return number of days after which this request will automatically expire if not completed.
         */
        public Integer getDaysValid() {
            return this.daysValid;
        }

        /**
         * Gets the reference id in an external system that this sign request is related to.
         *
         * @return external id.
         */
        public String getExternalId() {
            return this.externalId;
        }

        /**
         * Gets the URL that can be used by the sign request sender to prepare the document through the UI.
         *
         * @return prepare url.
         */
        public String getPrepareUrl() {
            return this.prepareUrl;
        }

        /**
         * Gets the reference to a file that will hold a log of all signer activity for this request.
         *
         * @return signing log.
         */
        public BoxFile.Info getSigningLog() {
            return this.signingLog;
        }

        /**
         * Gets the status of the sign request.
         *
         * @return sign request's status.
         */
        public BoxSignRequestStatus getStatus() {
            return this.status;
        }

        /**
         * Gets the list of files that signing events will occur on -
         * these are copies of the original source files.
         * These files will be updated as signers add inputs to the file
         * and can be downloaded at any point in the signing process.
         *
         * @return sign files.
         */
        public BoxSignRequestSignFiles getSignFiles() {
            return this.signFiles;
        }

        /**
         * Gets the date and time calculated using daysValid after
         * which a non finished document will be automatically expired.
         *
         * @return auto expires at date.
         */
        public Date getAutoExpireAt() {
            return this.autoExpireAt;
        }

        /**
         * Gets the date/time that the sign request was created.
         *
         * @return created at date.
         */
        public Date getCreatedAt() {
            return this.createdAt;
        }

        /**
         * Gets the date/time that the sign request was last updated.
         *
         * @return update at date.
         */
        public Date getUpdatedAt() {
            return this.updatedAt;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BoxSignRequest getResource() {
            return BoxSignRequest.this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        void parseJSONMember(JsonObject.Member member) {
            super.parseJSONMember(member);
            String memberName = member.getName();
            JsonValue value = member.getValue();
            try {
                if (memberName.equals("is_document_preparation_needed")) {
                    this.isDocumentPreparationNeeded = value.asBoolean();
                } else if (memberName.equals("redirect_url")) {
                    this.redirectUrl = value.asString();
                } else if (memberName.equals("declined_redirect_url")) {
                    this.declinedRedirectUrl = value.asString();
                } else if (memberName.equals("required_attachments")) {
                    List<BoxSignRequestRequiredAttachment> attachments =
                            new ArrayList<BoxSignRequestRequiredAttachment>();
                    for (JsonValue attachmentJSON : value.asArray()) {
                        BoxSignRequestRequiredAttachment attachment =
                                new BoxSignRequestRequiredAttachment(attachmentJSON.asObject());
                        attachments.add(attachment);
                    }
                    this.requiredAttachments = attachments;
                } else if (memberName.equals("are_attachments_enabled")) {
                    this.areAttachmentsEnabled = value.asBoolean();
                } else if (memberName.equals("are_text_signatures_enabled")) {
                    this.areTextSignaturesEnabled = value.asBoolean();
                } else if (memberName.equals("is_text_enabled")) {
                    this.isTextEnabled = value.asBoolean();
                } else if (memberName.equals("are_dates_enabled")) {
                    this.areDatesEnabled = value.asBoolean();
                } else if (memberName.equals("are_emails_disabled")) {
                    this.areEmailsDisabled = value.asBoolean();
                } else if (memberName.equals("signature_color")) {
                    this.signatureColor = BoxSignRequestSignatureColor.fromJSONString(value.asString());
                } else if (memberName.equals("is_phone_verification_required_to_view")) {
                    this.isPhoneVerificationRequiredToView = value.asBoolean();
                } else if (memberName.equals("email_subject")) {
                    this.emailSubject = value.asString();
                } else if (memberName.equals("email_message")) {
                    this.emailMessage = value.asString();
                } else if (memberName.equals("are_reminders_enabled")) {
                    this.areRemindersEnabled = value.asBoolean();
                } else if (memberName.equals("signers")) {
                    List<BoxSignRequestSigner> signers = new ArrayList<BoxSignRequestSigner>();
                    for (JsonValue signerJSON : value.asArray()) {
                        BoxSignRequestSigner signer = new BoxSignRequestSigner(signerJSON.asObject(), getAPI());
                        signers.add(signer);
                    }
                    this.signers = signers;
                } else if (memberName.equals("source_files")) {
                    List<BoxFile.Info> files = new ArrayList<BoxFile.Info>();
                    for (JsonValue fileJSON : value.asArray()) {
                        String fileID = fileJSON.asObject().get("id").asString();
                        BoxFile file = new BoxFile(getAPI(), fileID);
                        files.add(file.new Info(fileJSON.asObject()));
                    }
                    this.sourceFiles = files;
                } else if (memberName.equals("parent_folder")) {
                    JsonObject folderJSON = value.asObject();
                    String folderID = folderJSON.get("id").asString();
                    BoxFolder folder = new BoxFolder(getAPI(), folderID);
                    this.parentFolder = folder.new Info(folderJSON);
                } else if (memberName.equals("name")) {
                    this.name = value.asString();
                } else if (memberName.equals("prefill_tags")) {
                    List<BoxSignRequestPrefillTag> prefillTags = new ArrayList<BoxSignRequestPrefillTag>();
                    for (JsonValue prefillTagJSON : value.asArray()) {
                        BoxSignRequestPrefillTag prefillTag = new BoxSignRequestPrefillTag(prefillTagJSON.asObject());
                        prefillTags.add(prefillTag);
                    }
                    this.prefillTags = prefillTags;
                } else if (memberName.equals("days_valid")) {
                    this.daysValid = value.asInt();
                } else if (memberName.equals("external_id")) {
                    this.externalId = value.asString();
                } else if (memberName.equals("prepare_url")) {
                    this.prepareUrl = value.asString();
                } else if (memberName.equals("signing_log")) {
                    JsonObject signingLogJSON = value.asObject();
                    String fileID = signingLogJSON.get("id").asString();
                    BoxFile file = new BoxFile(getAPI(), fileID);
                    this.signingLog = file.new Info(signingLogJSON);
                } else if (memberName.equals("status")) {
                    this.status = BoxSignRequestStatus.fromJSONString(value.asString());
                } else if (memberName.equals("sign_files")) {
                    JsonObject signFilesJSON = value.asObject();
                    JsonValue filesArray = signFilesJSON.get("files");
                    List<BoxFile.Info> files = new ArrayList<BoxFile.Info>();
                    for (JsonValue fileJSON : filesArray.asArray()) {
                        String fileID = fileJSON.asObject().get("id").asString();
                        BoxFile file = new BoxFile(getAPI(), fileID);
                        files.add(file.new Info(fileJSON.asObject()));
                    }
                    boolean isReadyForDownload = signFilesJSON.get("is_ready_for_download").asBoolean();
                    this.signFiles = new BoxSignRequestSignFiles(files, isReadyForDownload);
                } else if (memberName.equals("auto_expire_at")) {
                    this.autoExpireAt = BoxDateFormat.parse(value.asString());
                } else if (memberName.equals("created_at")) {
                    this.createdAt = BoxDateFormat.parse(value.asString());
                } else if (memberName.equals("updated_at")) {
                    this.updatedAt = BoxDateFormat.parse(value.asString());
                }
            } catch (Exception e) {
                throw new BoxDeserializationException(memberName, value.toString(), e);
            }
        }

        /**
         * List of files that signing events will occur on - these are copies of the original source files.
         * These files will be updated as signers add inputs to the file
         * and can be downloaded at any point in the signing process.
         */
        public class BoxSignRequestSignFiles {
            private List<BoxFile.Info> files;
            private boolean isReadyToDownload;

            /**
             * Constructs a BoxSignRequestSignFiles.
             *
             * @param files             list that signing events will occur on.
             * @param isReadyToDownload indicating whether a change to the document is processing.
             */
            public BoxSignRequestSignFiles(List<BoxFile.Info> files, boolean isReadyToDownload) {
                this.files = files;
                this.isReadyToDownload = isReadyToDownload;
            }

            /**
             * Gets the list of files that signing events will occur on - these are copies of the original source files.
             *
             * @return list of files.
             */
            public List<BoxFile.Info> getFiles() {
                return this.files;
            }

            /**
             * Gets the flag indicating whether a change to the document is processing and the PDF may be out of date.
             * It is recommended to wait until processing has finished before downloading the PDF.
             * Webhooks are not sent until processing has been completed.
             *
             * @return true if files are ready to download, otherwise false.
             */
            public boolean getIsReadyToDownload() {
                return this.isReadyToDownload;
            }
        }
    }

    /**
     * Represents a status of the sign request.
     */
    public enum BoxSignRequestStatus {

        /**
         * Converting status.
         */
        Converting("converting"),

        /**
         * Created status.
         */
        Created("created"),

        /**
         * Sent status.
         */
        Sent("status"),

        /**
         * Viewed status.
         */
        Viewed("viewed"),

        /**
         * Signed status.
         */
        Signed("signed"),

        /**
         * Cancelled status.
         */
        Cancelled("cancelled"),

        /**
         * Declined status.
         */
        Declined("declined"),

        /**
         * Error converting status.
         */
        ErrorConverting("error_converting"),

        /**
         * Error sending status.
         */
        ErrorSending("error_sending"),

        /**
         * Expired status.
         */
        Expired("expired");

        private final String jsonValue;

        private BoxSignRequestStatus(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        static BoxSignRequestStatus fromJSONString(String jsonValue) {
            if (jsonValue.equals("converting")) {
                return Converting;
            } else if (jsonValue.equals("created")) {
                return Created;
            } else if (jsonValue.equals("sent")) {
                return Sent;
            } else if (jsonValue.equals("viewed")) {
                return Viewed;
            } else if (jsonValue.equals("signed")) {
                return Signed;
            } else if (jsonValue.equals("cancelled")) {
                return Cancelled;
            } else if (jsonValue.equals("declined")) {
                return Declined;
            } else if (jsonValue.equals("error_converting")) {
                return ErrorConverting;
            } else if (jsonValue.equals("error_sending")) {
                return ErrorSending;
            } else if (jsonValue.equals("expired")) {
                return Expired;
            } else {
                throw new IllegalArgumentException("The provided JSON value isn't a valid sing request status.");
            }
        }
    }
}
