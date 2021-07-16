package com.box.sdk;

import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

/**
 * Optional parameters for creating a Sign Request.
 *
 * @see BoxSignRequest
 */
public class BoxSignRequestCreateParams {

    private Boolean isDocumentPreparationNeeded;
    private String redirectUrl;
    private String declinedRedirectUrl;
    private List<BoxSignRequestRequiredAttachment> requiredAttachments;
    private Boolean areAttachmentsEnabled;
    private Boolean areTextSignaturesEnabled;
    private Boolean isTextEnabled;
    private Boolean areDatesEnabled;
    private Boolean areEmailsDisabled;
    private BoxSignRequestSignatureColor signatureColor;
    private Boolean isPhoneVerificationRequiredToView;
    private String emailSubject;
    private String emailMessage;
    private Boolean areRemindersEnabled;
    private String name;
    private List<BoxSignRequestPrefillTag> prefillTags;
    private Integer daysValid;
    private String externalId;

    /**
     * Gets the flag indicating if the sender should be taken into the builder flow to prepare the document.
     *
     * @return true if document preparation is needed, otherwise false.
     */
    public boolean getIsDocumentPreparationNeeded() {
        return this.isDocumentPreparationNeeded;
    }

    /**
     * Sets the flag indicating if the sender should be taken into the builder flow to prepare the document.
     *
     * @param isDocumentPreparationNeeded whether or not sender should be taken
     *                                    into the builder flow to prepare the document.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setIsDocumentPreparationNeeded(boolean isDocumentPreparationNeeded) {
        this.isDocumentPreparationNeeded = isDocumentPreparationNeeded;
        return this;
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
     * Sets the uri that a signer will be redirect to after signing a document.
     * If no declined redirect url is specified, this will be used for decline actions as well.
     *
     * @param redirectUrl used for redirection.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
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
     * Sets the uri that a signer will be redirect to after declining to sign a document.
     *
     * @param declinedRedirectUrl used for redirection.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setDeclinedRedirectUrl(String declinedRedirectUrl) {
        this.declinedRedirectUrl = declinedRedirectUrl;
        return this;
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
     * Sets the flag indicating if uploading/adding attachments for signers is enabled.
     *
     * @param areAttachmentsEnabled indicating if attachments uploading/adding is enabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setAreAttachmentsEnabled(boolean areAttachmentsEnabled) {
        this.areAttachmentsEnabled = areAttachmentsEnabled;
        return this;
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
     * Sets the flag indicating if usage of signatures generated by typing (text) is enabled.
     *
     * @param areTextSignaturesEnabled indicating if uploading/adding attachments for signers is enabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setAreTextSignaturesEnabled(boolean areTextSignaturesEnabled) {
        this.areTextSignaturesEnabled = areTextSignaturesEnabled;
        return this;
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
     * Sets the flag indicating if ability for signer to add text is enabled.
     *
     * @param isTextEnabled indicating if  usage of signatures generated by typing (text) is enabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setIsTextEnabled(boolean isTextEnabled) {
        this.isTextEnabled = isTextEnabled;
        return this;
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
     * Sets the flag indicating if ability for signer to add dates is enabled.
     *
     * @param areDatesEnabled indicating if ability for signer to add dates is enabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setAreDatesEnabled(boolean areDatesEnabled) {
        this.areDatesEnabled = areDatesEnabled;
        return this;
    }

    /**
     * Gets the flag indicating if all status emails, as well as the original email
     * that contains the sign request are disabled.
     *
     * @return true if emails are disabled, otherwise false.
     */
    public boolean getAreEmailsDisabled() {
        return this.areEmailsDisabled;
    }

    /**
     * Sets the flag indicating if all status emails, as well as the original email
     * that contains the sign request are disabled.
     *
     * @param areEmailsDisabled indicating if all status emails, as well as the original email
     *                          that contains the sign request are disabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setAreEmailsDisabled(boolean areEmailsDisabled) {
        this.areEmailsDisabled = areEmailsDisabled;
        return this;
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
     * Sets the forced, specific color for the signature.
     *
     * @param signatureColor blue, black or red.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setSignatureColor(BoxSignRequestSignatureColor signatureColor) {
        this.signatureColor = signatureColor;
        return this;
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
     * Sets the flag indicating if signers are forced to verify a text message prior to viewing the document.
     *
     * @param isPhoneVerificationRequiredToView indicating if signers are forced
     *                                          to verify a text message prior to viewing the document.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setIsPhoneVerificationRequiredToView(boolean isPhoneVerificationRequiredToView) {
        this.isPhoneVerificationRequiredToView = isPhoneVerificationRequiredToView;
        return this;
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
     * Sets the subject of sign request email. This is cleaned by sign request.
     *
     * @param emailSubject included in sign request email.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
        return this;
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
     * Sets the message to include in sign request email. This is cleaned,but some html tags are allowed.
     * Links included in the message are also converted to actual links in the email.
     * The message may contain the following html tags:
     * a, abbr, acronym, b, blockquote, code, em, i, ul, li, ol, and strong.
     * Be aware that when the text to html ratio is too high, the email may end up in spam filters.
     * Custom styles on these tags are not allowed.
     *
     * @param emailMessage included in sign request email.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
        return this;
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
     * Sets the flag indicating if remind for signers to sign a document on day 3, 8, 13 and 18
     * (or less if the document has been digitally signed already) is enabled.
     *
     * @param areRemindersEnabled indicating if reminders are enabled.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setAreRemindersEnabled(boolean areRemindersEnabled) {
        this.areRemindersEnabled = areRemindersEnabled;
        return this;
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
     * Sets the name of this sign request.
     *
     * @param name of this sign request.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the number of days after which this request will automatically expire if not completed.
     *
     * @return number of days after which this request will automatically expire if not completed.
     */
    public int getDaysValid() {
        return this.daysValid;
    }

    /**
     * Sets the number of days after which this request will automatically expire if not completed.
     *
     * @param daysValid of this sign request.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setDaysValid(int daysValid) {
        this.daysValid = daysValid;
        return this;
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
     * Sets the reference id in an external system that this sign request is related to.
     *
     * @param externalId of this sign request.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
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
     * Sets the attachments that signers are required to upload.
     *
     * @param requiredAttachments list for this sign request.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setRequiredAttachments(List<BoxSignRequestRequiredAttachment>
                                                                     requiredAttachments) {
        this.requiredAttachments = requiredAttachments;
        return this;
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
     * Sets the list of prefill tags. When a document contains sign related tags in the content,
     * you can prefill them using this prefillTags by referencing
     * the 'id' of the tag as the externalId field of the prefill tag.
     *
     * @param prefillTags list for this sign request.
     * @return this BoxSignRequestCreateParams object for chaining.
     */
    public BoxSignRequestCreateParams setPrefillTags(List<BoxSignRequestPrefillTag> prefillTags) {
        this.prefillTags = prefillTags;
        return this;
    }

    /**
     * Used to append BoxSignRequestCreateParams to request.
     *
     * @param requestJSON request in json to append data to.
     */
    public void appendParamsAsJson(JsonObject requestJSON) {
        if (this.isDocumentPreparationNeeded != null) {
            requestJSON.add("is_document_preparation_needed", this.isDocumentPreparationNeeded);
        }
        if (this.redirectUrl != null) {
            requestJSON.add("redirect_url", this.redirectUrl);
        }
        if (this.declinedRedirectUrl != null) {
            requestJSON.add("declined_redirect_url", this.declinedRedirectUrl);
        }
        if (this.areAttachmentsEnabled != null) {
            requestJSON.add("are_attachments_enabled", this.areAttachmentsEnabled);
        }
        if (this.areTextSignaturesEnabled != null) {
            requestJSON.add("are_text_signatures_enabled", this.areTextSignaturesEnabled);
        }
        if (this.isTextEnabled != null) {
            requestJSON.add("is_text_enabled", this.isTextEnabled);
        }
        if (this.areDatesEnabled != null) {
            requestJSON.add("are_dates_enabled", this.areDatesEnabled);
        }
        if (this.signatureColor != null) {
            requestJSON.add("signature_color", this.signatureColor.name().toLowerCase());
        }
        if (this.isPhoneVerificationRequiredToView != null) {
            requestJSON.add("is_phone_verification_required_to_view", this.isPhoneVerificationRequiredToView);
        }
        if (this.emailSubject != null) {
            requestJSON.add("email_subject", this.emailSubject);
        }
        if (this.emailMessage != null) {
            requestJSON.add("email_message", this.emailMessage);
        }
        if (this.areRemindersEnabled != null) {
            requestJSON.add("are_reminders_enabled", this.areRemindersEnabled);
        }
        if (this.name != null) {
            requestJSON.add("name", this.name);
        }
        if (this.daysValid != null) {
            requestJSON.add("days_valid", this.daysValid);
        }
        if (this.externalId != null) {
            requestJSON.add("external_id", this.externalId);
        }

        if (this.requiredAttachments != null) {
            JsonArray requiredAttachmentsJSON = new JsonArray();
            for (BoxSignRequestRequiredAttachment requiredAttachment : this.requiredAttachments) {
                requiredAttachmentsJSON.add(requiredAttachment.getJSONObject());
            }
            requestJSON.add("required_attachments", requiredAttachmentsJSON);
        }

        if (this.prefillTags != null) {
            JsonArray prefillTagsJSON = new JsonArray();
            for (BoxSignRequestPrefillTag prefillTag : this.prefillTags) {
                prefillTagsJSON.add(prefillTag.getJSONObject());
            }
            requestJSON.add("prefill_tags", prefillTagsJSON);
        }

        return;
    }
}
