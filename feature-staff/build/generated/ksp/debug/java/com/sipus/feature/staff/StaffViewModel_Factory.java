package com.sipus.feature.staff;

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
public final class StaffViewModel_Factory implements Factory<StaffViewModel> {
  private final Provider<SipusRepository> repoProvider;

  public StaffViewModel_Factory(Provider<SipusRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public StaffViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static StaffViewModel_Factory create(Provider<SipusRepository> repoProvider) {
    return new StaffViewModel_Factory(repoProvider);
  }

  public static StaffViewModel newInstance(SipusRepository repo) {
    return new StaffViewModel(repo);
  }
}
