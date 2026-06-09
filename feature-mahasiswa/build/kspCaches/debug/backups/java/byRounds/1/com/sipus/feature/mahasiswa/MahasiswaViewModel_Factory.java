package com.sipus.feature.mahasiswa;

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
public final class MahasiswaViewModel_Factory implements Factory<MahasiswaViewModel> {
  private final Provider<SipusRepository> repoProvider;

  private final Provider<SessionManager> sessionProvider;

  public MahasiswaViewModel_Factory(Provider<SipusRepository> repoProvider,
      Provider<SessionManager> sessionProvider) {
    this.repoProvider = repoProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public MahasiswaViewModel get() {
    return newInstance(repoProvider.get(), sessionProvider.get());
  }

  public static MahasiswaViewModel_Factory create(Provider<SipusRepository> repoProvider,
      Provider<SessionManager> sessionProvider) {
    return new MahasiswaViewModel_Factory(repoProvider, sessionProvider);
  }

  public static MahasiswaViewModel newInstance(SipusRepository repo, SessionManager session) {
    return new MahasiswaViewModel(repo, session);
  }
}
