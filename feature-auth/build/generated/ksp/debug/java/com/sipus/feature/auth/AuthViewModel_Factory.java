package com.sipus.feature.auth;

import com.sipus.core.data.datastore.SessionManager;
import com.sipus.core.data.repository.SipusRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SipusRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public AuthViewModel_Factory(Provider<SipusRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<SipusRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new AuthViewModel_Factory(repositoryProvider, sessionManagerProvider);
  }

  public static AuthViewModel newInstance(SipusRepository repository,
      SessionManager sessionManager) {
    return new AuthViewModel(repository, sessionManager);
  }
}
