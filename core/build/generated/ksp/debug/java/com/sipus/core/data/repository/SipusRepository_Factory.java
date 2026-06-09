package com.sipus.core.data.repository;

import com.sipus.core.data.local.dao.BookDao;
import com.sipus.core.data.local.dao.LoanDao;
import com.sipus.core.data.remote.SipusApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SipusRepository_Factory implements Factory<SipusRepository> {
  private final Provider<SipusApiService> apiProvider;

  private final Provider<BookDao> bookDaoProvider;

  private final Provider<LoanDao> loanDaoProvider;

  public SipusRepository_Factory(Provider<SipusApiService> apiProvider,
      Provider<BookDao> bookDaoProvider, Provider<LoanDao> loanDaoProvider) {
    this.apiProvider = apiProvider;
    this.bookDaoProvider = bookDaoProvider;
    this.loanDaoProvider = loanDaoProvider;
  }

  @Override
  public SipusRepository get() {
    return newInstance(apiProvider.get(), bookDaoProvider.get(), loanDaoProvider.get());
  }

  public static SipusRepository_Factory create(Provider<SipusApiService> apiProvider,
      Provider<BookDao> bookDaoProvider, Provider<LoanDao> loanDaoProvider) {
    return new SipusRepository_Factory(apiProvider, bookDaoProvider, loanDaoProvider);
  }

  public static SipusRepository newInstance(SipusApiService api, BookDao bookDao, LoanDao loanDao) {
    return new SipusRepository(api, bookDao, loanDao);
  }
}
