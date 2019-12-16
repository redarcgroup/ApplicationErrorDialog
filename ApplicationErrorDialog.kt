class ApplicationErrorDialog : DialogFragment() {
    private lateinit var listener: ApplicationErrorDialogListener
    var title: String? = null
    var message: String? = null
    var positiveButtonCaption: String? = null
    var showNeutralButton = true

    companion object {
        fun newInstance() = ApplicationErrorDialog().apply {
            arguments = Bundle()
        }
    }

    interface ApplicationErrorDialogListener {
        fun onRetryActionClick(dialog: DialogFragment) {/* Default Implementation */}
        fun onCancelActionClick(dialog: DialogFragment) {/* Default Implementation */}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //Check if coming from a fragment
            listener = parentFragment as? ApplicationErrorDialogListener ?: context as ApplicationErrorDialogListener
        }
        catch (exception: ClassCastException) {
            // If the interface is not implemented, throw exception
            throw ClassCastException((context.toString() + exception.message))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        try {
            //Set tile and message
            val dialogBuilder = MaterialAlertDialogBuilder(activity, R.style.AlertDialogTheme)
            dialogBuilder.setTitle(title ?: getString(R.string.application_error_title))
            dialogBuilder.setMessage(message ?: getString(R.string.application_error_message))
            dialogBuilder.setIcon(R.drawable.ic_error_outline)
            dialogBuilder.setPositiveButton(positiveButtonCaption ?: getString(R.string.retry_button)) { _, _ ->
                // Send the Positive (Confirm) button click event back to parent fragment
                listener.onRetryActionClick(this)
            }

            if(showNeutralButton) {
                dialogBuilder.setNeutralButton(R.string.cancel_button) { _, _ ->
                    // Send the Positive (Confirm) button click event back to parent fragment
                    listener.onCancelActionClick(this)
                }
            }
            return (dialogBuilder.create())
        }
        catch (exception: Exception) {
            throw IllegalStateException(getString(R.string.application_error_message))
        }
    }
}
