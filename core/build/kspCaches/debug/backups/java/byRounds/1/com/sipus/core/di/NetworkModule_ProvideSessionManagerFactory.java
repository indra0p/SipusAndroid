package com.sipus.core.di;

import android.content.Context;
import com.sipus.core.data.datastore.SessionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NetworkModule_ProvideSessionManagerFactory implements Factory<SessionManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideSessionManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SessionManager get() {
    return provideSessionManager(contextProvider.get());
  }

  public static NetworkModule_ProvideSessionManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideSessionManagerFactory(contextProvider);
  }

  public static SessionManager provideSessionManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSessionManager(context));
  }
}
