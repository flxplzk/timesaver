package de.flxplzk.vaadin.common;

import java.util.concurrent.ForkJoinPool;

/**
 * This implementation aims to simplify the execution of AsyncTasks on a different thread.
 *
 * @param <P> Type of the params the doInBackground method
 * @param <R> Type of the computed Result
 */
public abstract class AsyncTask<P, R> {


    private boolean isExecuting = false;

    /**
     * is called to fire an AsyncTask. when finished it will call onPostExecute,
     * for instance to post an AsyncTaskFinishedEvent to the EventBus
     *
     * @param params Type of the params the doInBackground method
     */
    public void execute(P... params) {
        if (!isExecuting) {
            this.isExecuting = true;
            this.onPreExecute();
            ForkJoinPool.commonPool().submit(() -> {
                try {
                    R result = doInBackground(params);
                    this.isExecuting = false;
                    this.onPostExecute(result);
                } catch (RuntimeException cause) {
                    this.onError(cause);
                }
            });
        } else{
            throw new CommonAddOnException.AsyncTaskException("Task is already executing");
        }
    }

    protected void onPreExecute () {
    }

    /**
     * the task to compute hast to be defined in this method
     *
     * @param params e.g. URLs, args ...
     * @return
     */
    protected abstract R doInBackground (P...params);

    /**
     * notification to the user should be done in this method
     */
    protected abstract void onPostExecute (R result);

    protected void onError (RuntimeException cause){
        throw cause;
    }

}
