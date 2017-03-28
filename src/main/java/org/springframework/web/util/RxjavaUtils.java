package org.springframework.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;




import org.springframework.samples.mvc.jpa.entity.DownloadRepoMessageEvent;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


@SuppressWarnings("unused")
public class RxjavaUtils {

	private final static CompositeSubscription mAllSubscription = new CompositeSubscription();

	public static void main(String[] args) {

		// rxBus();//rxbus����

		 observeOn();//�۲��¼�

		// subscribeOn();//�����¼�

		//doOnNext();// doOnNext()
	}

	private static void doOnNext() {
	    Observable.create(new Observable.OnSubscribe<Integer>() {
		@Override
		public void call(Subscriber<? super Integer> subscriber) {
	            subscriber.onNext(1);
	            subscriber.onNext(2);
	            subscriber.onNext(3);
	            subscriber.onNext(4);
	            //subscriber.onCompleted();
		}
	     })
	     .subscribeOn(getNamedScheduler("�߳�1"))
         .doOnNext(new Action1<Integer>() {
          @Override
          public void call(Integer item) {
        	  threadInfo("doOnNext:"+item);
        	  try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//            if( item > 3 ) {
//              throw new RuntimeException( "Item exceeds maximum value" );
//            }
          }
        }).subscribe(new Subscriber<Integer>() {
        @Override
        public void onNext(Integer item) {
            System.out.println("Next: " + item);
        }

        @Override
        public void onError(Throwable error) {
            System.err.println("Error: " + error.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Sequence complete.");
        }
    });
	}

	/**
	 * subscribeOn() �����¼�
	 */
	public static void subscribeOn() {
		Observable
				.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						threadInfo("��ʱ����2�� OnSubscribe.call()");
						subscriber.onNext("��ʱ����2-RxJava");
					}
				})
				.subscribeOn(getNamedScheduler("�߳�2"))
				// �������߳�2
				.doOnSubscribe(() -> threadInfo("��ʱ����1"))
				.subscribeOn(getNamedScheduler("�߳�1"))
				// �������߳�1
				.doOnSubscribe(() -> threadInfo("��ʱ����0"))
				.subscribeOn(getNamedScheduler("�߳�0��"))
				.doOnSubscribe(() -> threadInfo("��ʱ����")).subscribe(s -> { // �����ڵ�ǰ�߳�
							threadInfo(".onNext()");
							System.out.println(s + "-onNext");
						});
	}

	/**
	 * observeOn���� �۲��¼�
	 */
	public static void observeOn() {
		Observable.just("RxJava")
				.observeOn(getNamedScheduler("map֮ǰ��observeOn")).map(s -> {
					threadInfo(".map()-1");
					return s + "-map1";
				}).map(s -> {
					threadInfo(".map()-2");
					return s + "-map2";
				}).observeOn(getNamedScheduler("�Զ�����߳�����")).map(s -> {
					threadInfo(".map()-3");
					return s + "-�Զ����߳�����";
				}).observeOn(getNamedScheduler("subscribe֮ǰ��observeOn"))
				.subscribe(s -> {
					threadInfo(".onNext()");
					System.out.println("�۲��¼���ʽ���ã�" + s + "-onNext");
					System.out.println("----------------------------");
				});
	}

	/**
	 * ����rxbus
	 */
	public static void rxBus() {
		// ע�� ---���۲���
		registerSubscription(RxBus.getInstance().toObservable()
				.filter(o -> o instanceof DownloadRepoMessageEvent)
				.map(o -> (DownloadRepoMessageEvent) o)
				// .observeOn(AndroidSchedulers.mainThread())
				.doOnNext(o -> showMessage(o.getMessage())).subscribe());
		// ������Ϣ ---�۲���
		RxBus.getInstance().send(
				new DownloadRepoMessageEvent("����������rxjava������������Ϣ��"));
	}

	public static void showMessage(String message) {
		System.out.println(message);
	}

	public static void registerSubscription(Subscription subscription) {
		mAllSubscription.add(subscription);
	}

	protected void unregisterSubscription(Subscription subscription) {
		mAllSubscription.remove(subscription);
	}

	public static Scheduler getNamedScheduler(String name) {
		return Schedulers.from(Executors.newCachedThreadPool(r -> new Thread(r,
				name)));
	}

	public static void threadInfo(String caller) {
		System.out.println(caller + " => " + Thread.currentThread().getName());
	}

}

